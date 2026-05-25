package utils;

import java.util.ArrayList;
import java.util.List;

public class SkillMatcher {

    public static List<String> getMatchedSkills(List<String> userSkills, List<String> roleSkills) {
        List<String> matched = new ArrayList<>();
        for (String role : roleSkills) {
            for (String user : userSkills) {
                if (role.equalsIgnoreCase(user.trim())) {
                    matched.add(role);
                    break;
                }
            }
        }
        return matched;
    }

    public static List<String> getMissingSkills(List<String> userSkills, List<String> roleSkills) {
        List<String> missing = new ArrayList<>();
        for (String role : roleSkills) {
            boolean found = false;
            for (String user : userSkills) {
                if (role.equalsIgnoreCase(user.trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) missing.add(role);
        }
        return missing;
    }

    public static int calculateScore(List<String> matched, List<String> total) {
        if (total.isEmpty()) return 0;
        return (int) ((matched.size() / (double) total.size()) * 100);
    }
}