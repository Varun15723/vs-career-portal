-- ============================================================
-- Profile Management - Extended Schema (9 modules)
-- File: careerportal/src/main/resources/db/profile_extended_tables.sql
-- Run AFTER the base profile_tables.sql
-- All candidate_* tables reference candidate_profiles.id (NOT a separate users table)
-- ============================================================

CREATE TABLE IF NOT EXISTS candidate_education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    degree VARCHAR(150) NOT NULL,
    field_of_study VARCHAR(150),
    institution VARCHAR(250) NOT NULL,
    board_or_university VARCHAR(200),
    start_year INT,
    end_year INT,
    is_pursuing BOOLEAN NOT NULL DEFAULT FALSE,
    grade_or_percentage VARCHAR(20),
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_edu_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_edu_candidate (candidate_id)
);

CREATE TABLE IF NOT EXISTS candidate_experience (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    job_title VARCHAR(150) NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    employment_type VARCHAR(50),
    location VARCHAR(150),
    is_remote BOOLEAN NOT NULL DEFAULT FALSE,
    start_date DATE,
    end_date DATE,
    is_current BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    skills_used TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_exp_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_exp_candidate (candidate_id)
);

CREATE TABLE IF NOT EXISTS candidate_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    proficiency_level VARCHAR(20),
    years_of_experience INT,
    category VARCHAR(80),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_candidate_skill (candidate_id, skill_name),
    CONSTRAINT fk_skill_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_skill_name (skill_name)
);

CREATE TABLE IF NOT EXISTS candidate_certifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    issuing_organization VARCHAR(200) NOT NULL,
    credential_id VARCHAR(150),
    credential_url VARCHAR(500),
    issue_date DATE,
    expiry_date DATE,
    does_not_expire BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cert_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_cert_candidate (candidate_id)
);

CREATE TABLE IF NOT EXISTS candidate_languages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    language VARCHAR(80) NOT NULL,
    proficiency VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_candidate_language (candidate_id, language),
    CONSTRAINT fk_lang_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS candidate_portfolio_links (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(500) NOT NULL,
    description TEXT,
    technology_stack VARCHAR(300),
    link_type VARCHAR(50),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_portfolio_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_portfolio_candidate (candidate_id)
);

CREATE TABLE IF NOT EXISTS candidate_social_links (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    platform VARCHAR(50) NOT NULL,
    url VARCHAR(500) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_candidate_platform (candidate_id, platform),
    CONSTRAINT fk_social_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS candidate_resume_versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    version_label VARCHAR(100) NOT NULL,
    file_url VARCHAR(500) NOT NULL,
    file_name VARCHAR(255),
    file_size_kb INT,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    ai_score INT,            -- reserved for future AI scoring feature, not used yet
    ai_feedback TEXT,        -- reserved for future AI scoring feature, not used yet
    uploaded_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_resume_candidate FOREIGN KEY (candidate_id)
        REFERENCES candidate_profiles(id) ON DELETE CASCADE,
    INDEX idx_resume_candidate (candidate_id),
    INDEX idx_resume_primary (candidate_id, is_primary)
);

-- FIXED: removed user_id / users table dependency (no generic users table exists
-- in this project). Team members are identified by email against the recruiter
-- login table instead, matching how the rest of the app is built.
CREATE TABLE IF NOT EXISTS recruiter_team_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recruiter_profile_id BIGINT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    designation VARCHAR(150),
    role VARCHAR(30) NOT NULL DEFAULT 'MEMBER',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    invited_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    joined_at DATETIME,
    CONSTRAINT fk_team_recruiter FOREIGN KEY (recruiter_profile_id)
        REFERENCES recruiter_profiles(id) ON DELETE CASCADE,
    INDEX idx_team_recruiter (recruiter_profile_id),
    UNIQUE KEY uq_recruiter_email (recruiter_profile_id, email)
);
