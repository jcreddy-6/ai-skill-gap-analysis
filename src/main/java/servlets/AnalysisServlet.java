package servlets;

import utils.DBConnection;
import utils.SkillMatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/analyze")
public class AnalysisServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        try {
            String userName = request.getParameter("userName");
            String skillsInput = request.getParameter("skills");
            String roleIdStr = request.getParameter("roleId");

            if (userName == null || skillsInput == null || roleIdStr == null) {
                out.print("{\"error\":\"Missing parameters: userName=" + userName + 
                    " skills=" + skillsInput + " roleId=" + roleIdStr + "\"}");
                return;
            }

            int roleId = Integer.parseInt(roleIdStr.trim());
            List<String> userSkills = Arrays.asList(skillsInput.split(","));

            try (Connection conn = DBConnection.getConnection()) {

                String roleName = "";
                PreparedStatement roleStmt = conn.prepareStatement(
                    "SELECT role_name FROM job_roles WHERE id = ?");
                roleStmt.setInt(1, roleId);
                ResultSet roleRs = roleStmt.executeQuery();
                if (roleRs.next()) roleName = roleRs.getString("role_name");

                List<String> essentialSkills = new ArrayList<>();
                List<String> allSkills = new ArrayList<>();

                PreparedStatement reqStmt = conn.prepareStatement(
                    "SELECT skill_name, priority FROM role_requirements WHERE role_id = ?");
                reqStmt.setInt(1, roleId);
                ResultSet reqRs = reqStmt.executeQuery();

                while (reqRs.next()) {
                    String skill = reqRs.getString("skill_name");
                    String priority = reqRs.getString("priority");
                    allSkills.add(skill);
                    if (priority.equals("essential")) essentialSkills.add(skill);
                }

                List<String> matched = SkillMatcher.getMatchedSkills(userSkills, allSkills);
                List<String> missing = SkillMatcher.getMissingSkills(userSkills, allSkills);
                List<String> missingEssential = SkillMatcher.getMissingSkills(userSkills, essentialSkills);
                int score = SkillMatcher.calculateScore(matched, allSkills);

                for (String skill : userSkills) {
                    PreparedStatement saveStmt = conn.prepareStatement(
                        "INSERT INTO user_skills (user_name, skill_name, proficiency) VALUES (?, ?, 'intermediate')");
                    saveStmt.setString(1, userName);
                    saveStmt.setString(2, skill.trim());
                    saveStmt.executeUpdate();
                }

                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"userName\":\"").append(userName).append("\",");
                json.append("\"roleName\":\"").append(roleName).append("\",");
                json.append("\"score\":").append(score).append(",");
                json.append("\"matched\":").append(toJsonArray(matched)).append(",");
                json.append("\"missing\":").append(toJsonArray(missing)).append(",");
                json.append("\"missingEssential\":").append(toJsonArray(missingEssential)).append(",");
                json.append("\"total\":").append(allSkills.size());
                json.append("}");

                out.print(json.toString());
            }

        } catch (Exception e) {
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private String toJsonArray(List<String> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append("\"").append(list.get(i)).append("\"");
            if (i < list.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}