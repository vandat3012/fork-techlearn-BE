CREATE DATABASE IF NOT EXISTS techlearn;

USE techlearn;

CREATE TABLE tbl_assignment
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    course_id     BIGINT                NULL,
    chapter_id    BIGINT                NULL,
    is_deleted    BIT(1)                NULL,
    CONSTRAINT pk_tbl_assignment PRIMARY KEY (id)
);

CREATE TABLE tbl_assignment_user
(
    assignment_id BIGINT     NOT NULL,
    user_id       BINARY(16) NOT NULL
);

CREATE TABLE tbl_chapter
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  created_by VARCHAR(255) NULL,
  created_date datetime NULL,
  modified_date datetime NULL,
  modified_by VARCHAR(255) NULL,
  title VARCHAR(255) NULL,
  `description` MEDIUMTEXT NULL,
  position INT NULL,
  course_id BIGINT NULL,
  is_deleted BIT(1) NULL,
  CONSTRAINT pk_tbl_chapter PRIMARY KEY (id)
);

CREATE TABLE tbl_course
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime NULL,
    modified_date datetime NULL,
    modified_by   VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    `description` MEDIUMTEXT NULL,
    is_deleted    BIT(1) NULL,
    CONSTRAINT pk_tbl_course PRIMARY KEY (id)
);

CREATE TABLE tbl_submission
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    link          VARCHAR(255)          NULL,
    status        VARCHAR(255)          NULL,
    course_id     BIGINT                NULL,
    user_id       BINARY(16)            NULL,
    assignment_id BIGINT                NULL,
    is_deleted    BIT(1)                NULL,
    CONSTRAINT pk_tbl_submission PRIMARY KEY (id)
);

CREATE TABLE tbl_submit_feedback
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_by      VARCHAR(255) NULL,
    created_date    datetime NULL,
    modified_date   datetime NULL,
    modified_by     VARCHAR(255) NULL,
    `description`   MEDIUMTEXT NULL,
    assignment_id   BIGINT NULL,
    link            VARCHAR(255) NULL,
    user_id         BINARY(16)            NULL,
    feedback_date   datetime NOT NULL,
    is_ai_generated BIT(1)   NOT NULL,
    feedback_type   VARCHAR(255) NULL,
    status          VARCHAR(255) NULL,
    is_deleted      BIT(1) NULL,
    CONSTRAINT pk_tbl_submit_feedback PRIMARY KEY (id)
);

CREATE TABLE tbl_user
(
    id            BINARY(16)   NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime     NULL,
    modified_date datetime     NULL,
    modified_by   VARCHAR(255) NULL,
    full_name     VARCHAR(255) NULL,
    age           INT          NULL,
    is_deleted    BIT(1)       NULL,
    CONSTRAINT pk_tbl_user PRIMARY KEY (id)
);

CREATE TABLE tbl_githublink
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    url           VARCHAR(255)          NULL,
    is_deleted    BIT(1)                NULL,
    CONSTRAINT pk_tbl_githublink PRIMARY KEY (id)
);

CREATE TABLE tbl_review
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    content       VARCHAR(255)          NULL,
    is_deleted    BIT(1)                NULL,
    CONSTRAINT pk_tbl_review PRIMARY KEY (id)
);

ALTER TABLE tbl_chapter
    ADD CONSTRAINT FK_TBL_CHAPTER_ON_COURSE FOREIGN KEY (course_id) REFERENCES tbl_course (id);

ALTER TABLE tbl_submit_feedback
    ADD CONSTRAINT FK_TBL_SUBMIT_FEEDBACK_ON_ASSIGNMENT FOREIGN KEY (assignment_id) REFERENCES tbl_assignment (id);

ALTER TABLE tbl_submit_feedback
    ADD CONSTRAINT FK_TBL_SUBMIT_FEEDBACK_ON_USER FOREIGN KEY (user_id) REFERENCES tbl_user (id);

ALTER TABLE tbl_assignment
    ADD CONSTRAINT FK_TBL_ASSIGNMENT_ON_CHAPTER FOREIGN KEY (chapter_id) REFERENCES tbl_chapter (id);

ALTER TABLE tbl_assignment
    ADD CONSTRAINT FK_TBL_ASSIGNMENT_ON_COURSE FOREIGN KEY (course_id) REFERENCES tbl_course (id);

ALTER TABLE tbl_assignment_user
    ADD CONSTRAINT fk_tblassuse_on_assignment_entity FOREIGN KEY (assignment_id) REFERENCES tbl_assignment (id);

ALTER TABLE tbl_assignment_user
    ADD CONSTRAINT fk_tblassuse_on_user_entity FOREIGN KEY (user_id) REFERENCES tbl_user (id);

ALTER TABLE tbl_submission
    ADD CONSTRAINT FK_TBL_SUBMISSION_ON_ASSIGNMENT FOREIGN KEY (assignment_id) REFERENCES tbl_assignment (id);

ALTER TABLE tbl_submission
    ADD CONSTRAINT FK_TBL_SUBMISSION_ON_COURSE FOREIGN KEY (course_id) REFERENCES tbl_course (id);

ALTER TABLE tbl_submission
    ADD CONSTRAINT FK_TBL_SUBMISSION_ON_USER FOREIGN KEY (user_id) REFERENCES tbl_user (id);
