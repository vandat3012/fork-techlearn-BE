Alter table tbl_user
    add column avatar     varchar(255) null,
    add column point      int          null,
    add column is_mentor  bit(1) default 0,
    add column is_teacher bit(1) default 0;

ALTER TABLE teacher
    ADD COLUMN email VARCHAR(255) NOT NULL;

INSERT INTO tbl_user (id, created_by, created_date, modified_date, modified_by, full_name, age, email, password, avatar,
                      is_deleted, is_mentor, is_teacher)
VALUES (UUID_TO_BIN(UUID()), 'system', NOW(), NOW(), 'system', 'Trần Sương', 25, 'suongtran@gmail.com',
        '$2a$12$hSMTKrTDZjQtNvCJ0xBNdeJenSamThzjYS5k9hAliS/SvYUBk9fxy', NULL, 0, 1, 0),
       (UUID_TO_BIN(UUID()), 'system', NOW(), NOW(), 'system', 'nguyễn Kim Tuyết', 20, 'nktuyet@gmail.com',
        '$2a$12$hSMTKrTDZjQtNvCJ0xBNdeJenSamThzjYS5k9hAliS/SvYUBk9fxy', NULL, 0, 0, 0),
       (UUID_TO_BIN(UUID()), 'system', NOW(), NOW(), 'system', 'Tuấn Nguyễn', 28, 'tuanng@gmail.com',
        '$2a$12$hSMTKrTDZjQtNvCJ0xBNdeJenSamThzjYS5k9hAliS/SvYUBk9fxy',
        'https://i.pinimg.com/564x/35/0e/5b/350e5b98dfecb7e6149ad545ad307617.jpg', 0, 0, 1),
       (UUID_TO_BIN(UUID()), 'system', NOW(), NOW(), 'system', 'Vi Trần', 19, 'tieuvi200904@gmail.com',
        '$2a$12$hSMTKrTDZjQtNvCJ0xBNdeJenSamThzjYS5k9hAliS/SvYUBk9fxy',
        'https://i.pinimg.com/564x/42/ce/fe/42cefe9ed9388b1feed1773e67aeba17.jpg', 0, 0, 0),
       (UUID_TO_BIN(UUID()), 'system', NOW(), NOW(), 'system', 'Đạt Phan', 32, 'datphan1@gmail.com',
        '$2a$12$hSMTKrTDZjQtNvCJ0xBNdeJenSamThzjYS5k9hAliS/SvYUBk9fxy',
        'https://i.pinimg.com/564x/1e/d4/4a/1ed44a71d525c0d904a1d52d11beccd0.jpg', 0, 0, 1);

insert into tbl_roles (id, name)
values (1, 'ADMIN'),
       (2, 'TEACHER'),
       (3, 'MENTOR'),
       (4, 'USER');

-- add roles
SET @user1_id = (SELECT id
                 FROM tbl_user
                 WHERE email = 'suongtran@gmail.com');

SET @user2_id = (SELECT id
                 FROM tbl_user
                 WHERE email = 'datphan1@gmail.com');

SET @user3_id = (SELECT id
                 FROM tbl_user
                 WHERE email = 'tuanng@gmail.com');

SET @user4_id = (SELECT id
                 FROM tbl_user
                 WHERE email = 'tieuvi200904@gmail.com');

INSERT INTO tbl_user_roles (user, role)
VALUES (@user1_id, 3),
       (@user2_id, 2),
       (@user3_id, 2),
       (@user4_id, 4);