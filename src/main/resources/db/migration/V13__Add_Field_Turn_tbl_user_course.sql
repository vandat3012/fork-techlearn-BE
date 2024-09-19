ALTER TABLE tbl_user_course
    ADD id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    ADD turn INT DEFAULT 30,
    ADD created_by       VARCHAR(255)          NULL,
    ADD created_date     datetime              NULL,
    ADD modified_date    datetime              NULL,
    ADD modified_by      VARCHAR(255)          NULL,
    ADD is_deleted       BIT(1)                NULL
;
INSERT INTO tbl_user (id, created_by, created_date, modified_date, modified_by, full_name, age, email, password,
                      is_deleted)
VALUES (UNHEX('6A1B4EBAFBC6412B82192A1F84EBA568'), 'admin', NOW(), NOW(), 'system', 'Trần Võ', 25, 'vo@gmail.com',
        '$2a$12$k5fluGyp4vSRccRd2Q1Btemdc/nvvds5z1qsyiljve0iETHFccXLq', 0),
       (UNHEX('6A1B4EBAFBC6412B82192A1F84EBA569'), 'admin', NOW(), NOW(), 'system', 'nguyễn Kim Tuyết', 20, 'nktuyet1@gmail.com',
        '$2a$12$k5fluGyp4vSRccRd2Q1Btemdc/nvvds5z1qsyiljve0iETHFccXLq', 0);

INSERT INTO tbl_user_course (id_course, id_user)
VALUES
    (1, UNHEX('6A1B4EBAFBC6412B82192A1F84EBA568')),
    (2, UNHEX('6A1B4EBAFBC6412B82192A1F84EBA568'));

