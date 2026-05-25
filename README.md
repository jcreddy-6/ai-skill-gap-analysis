# 📊 AI Skill Gap Analysis System

> A full-stack web application that compares a user's current skills against job role requirements and generates a personalised learning roadmap to bridge the gap.

---

## 🎯 What It Does

1. User enters their name and current skills (e.g. Python, HTML, MySQL)
2. User selects a target job role (e.g. Data Analyst, Full Stack Developer, ML Engineer)
3. System compares skills against role requirements stored in MySQL database
4. Displays a **gap report** — matched skills, missing skills, and gap score
5. Generates a **learning roadmap** with prioritised upskilling suggestions

---

## ✨ Features

- 🔍 **Skill Matching Engine** — Compares user skills vs. role requirements with scoring
- 📊 **Gap Score** — Visual percentage score showing how ready you are for the role
- 📋 **Gap Report** — Clear breakdown of matched and missing skills
- 🗺️ **Learning Roadmap** — Prioritised list of skills to learn, ordered by importance
- 🗃️ **Role Database** — Covers 5 job roles across software, data, and AI domains
- 💾 **Persistent Storage** — MySQL backend stores user skills and role data

---

## 🏗️ Architecture

```
Browser (HTML/CSS/JS)
      ↓  HTTP POST Request
Java Servlet (AnalysisServlet.java)
      ├── Skill comparison algorithm (SkillMatcher.java)
      ├── Gap score calculation
      └── Learning roadmap generation
      ↓  JDBC Connection
MySQL Database (skillgap)
      ├── job_roles table
      ├── role_requirements table
      └── user_skills table
      ↓
Embedded Apache Tomcat (via Maven)
```

---

## 🔧 Tech Stack

| Layer | Technology |
|-------|------------|
| Frontend | HTML5, CSS3, JavaScript |
| Backend | Java (Servlets, JDBC) |
| Server | Apache Tomcat 7 (Embedded via Maven) |
| Database | MySQL 5.5 |
| Build Tool | Maven |

---

## 🗄️ Database Schema

```sql
CREATE TABLE job_roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(100),
    description TEXT
);

CREATE TABLE role_requirements (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT,
    skill_name VARCHAR(100),
    priority ENUM('essential', 'preferred', 'bonus'),
    FOREIGN KEY (role_id) REFERENCES job_roles(id)
);

CREATE TABLE user_skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100),
    skill_name VARCHAR(100),
    proficiency ENUM('beginner', 'intermediate', 'advanced')
);
```

---

## 🚀 Setup & Run

### Prerequisites
- Java JDK 17+
- Maven 3.9+
- MySQL 5.5+

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/jcreddy-6/ai-skill-gap-analysis
cd ai-skill-gap-analysis

# 2. Set up the database
mysql -u root -p < database/schema.sql

# 3. Update DB password in DBConnection.java
# src/main/java/utils/DBConnection.java
# Change: private static final String PASSWORD = "your_password";

# 4. Run the app
mvn tomcat7:run

# 5. Open in browser
# http://localhost:8080/skillgap
```

---

## 📂 Project Structure

```
ai-skill-gap-analysis/
├── src/
│   └── main/
│       ├── java/
│       │   ├── servlets/
│       │   │   └── AnalysisServlet.java
│       │   └── utils/
│       │       ├── DBConnection.java
│       │       └── SkillMatcher.java
│       └── webapp/
│           ├── index.html
│           ├── script.js
│           ├── css/
│           │   └── style.css
│           └── WEB-INF/
│               └── web.xml
├── database/
│   └── schema.sql
├── pom.xml
└── README.md
```

---

## 📸 Sample Output

```
Name: Jhansi  |  Target Role: Data Analyst
─────────────────────────────────────────────
✅ Matched Skills (3/7): Python, Tableau, Excel
❌ Missing Skills (4/7): MySQL, R, Power BI, Statistics

Gap Score: 42% — Intermediate level 📈

📌 Learning Roadmap:
1. MySQL      — Essential  — ~3 weeks
2. R          — Essential  — ~4 weeks
3. Statistics — Essential  — ~3 weeks
─────────────────────────────────────────────
```

---

## 🎯 Supported Job Roles

| Role | Key Skills |
|------|-----------|
| Data Analyst | Python, MySQL, Tableau, R, Power BI |
| Full Stack Developer | HTML, CSS, JavaScript, React, Python |
| ML Engineer | Python, TensorFlow, Machine Learning |
| DevOps Engineer | Linux, Docker, Git, AWS, Kubernetes |
| Backend Developer | Java, MySQL, REST API, Git |

---

## 👤 Author

**Thigulla Jhansi Chandra Reddy**
2nd Year B.Tech CSE — Anurag University, Hyderabad
[LinkedIn](https://www.linkedin.com/in/jhansi-chandra-reddy-a14b15382/) | [GitHub](https://github.com/jcreddy-6)
