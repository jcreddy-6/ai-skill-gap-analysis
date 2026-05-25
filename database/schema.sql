-- =============================================
-- AI Skill Gap Analysis System
-- Database Schema + Seed Data
-- Author: Thigulla Jhansi Chandra Reddy
-- =============================================

-- Create and use database
CREATE DATABASE IF NOT EXISTS skillgap;
USE skillgap;

-- =============================================
-- TABLE 1: Job Roles
-- =============================================
CREATE TABLE job_roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(100),
    description TEXT
);

-- =============================================
-- TABLE 2: Role Requirements
-- =============================================
CREATE TABLE role_requirements (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT,
    skill_name VARCHAR(100),
    priority ENUM('essential', 'preferred', 'bonus'),
    FOREIGN KEY (role_id) REFERENCES job_roles(id)
);

-- =============================================
-- TABLE 3: User Skills
-- =============================================
CREATE TABLE user_skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100),
    skill_name VARCHAR(100),
    proficiency ENUM('beginner', 'intermediate', 'advanced')
);

-- =============================================
-- SEED DATA: Job Roles
-- =============================================
INSERT INTO job_roles (role_name, description) VALUES
('Data Analyst', 'Analyzes data to provide business insights'),
('Full Stack Developer', 'Builds both frontend and backend applications'),
('ML Engineer', 'Builds and deploys machine learning models'),
('DevOps Engineer', 'Manages infrastructure and deployment pipelines'),
('Backend Developer', 'Develops server-side applications and APIs');

-- =============================================
-- SEED DATA: Role Requirements
-- =============================================
INSERT INTO role_requirements (role_id, skill_name, priority) VALUES
-- Data Analyst
(1, 'Python', 'essential'),
(1, 'MySQL', 'essential'),
(1, 'Tableau', 'essential'),
(1, 'Excel', 'preferred'),
(1, 'R', 'essential'),
(1, 'Power BI', 'preferred'),
(1, 'Statistics', 'essential'),

-- Full Stack Developer
(2, 'HTML', 'essential'),
(2, 'CSS', 'essential'),
(2, 'JavaScript', 'essential'),
(2, 'React', 'preferred'),
(2, 'Python', 'essential'),
(2, 'MySQL', 'preferred'),
(2, 'Git', 'essential'),

-- ML Engineer
(3, 'Python', 'essential'),
(3, 'Machine Learning', 'essential'),
(3, 'TensorFlow', 'essential'),
(3, 'Statistics', 'essential'),
(3, 'SQL', 'preferred'),
(3, 'Deep Learning', 'preferred'),

-- DevOps Engineer
(4, 'Linux', 'essential'),
(4, 'Docker', 'essential'),
(4, 'Git', 'essential'),
(4, 'AWS', 'preferred'),
(4, 'Jenkins', 'preferred'),
(4, 'Kubernetes', 'bonus'),

-- Backend Developer
(5, 'Java', 'essential'),
(5, 'Python', 'preferred'),
(5, 'MySQL', 'essential'),
(5, 'REST API', 'essential'),
(5, 'Git', 'essential');