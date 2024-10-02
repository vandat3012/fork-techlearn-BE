SET @user_id = (SELECT id
                FROM tbl_user
                WHERE email = 'tieuvi200904@gmail.com');

INSERT INTO tbl_user_course (id_course, id_user)
VALUES (1, @user_id),
       (2, @user_id);

SET @teacher1_id = (SELECT id
                    FROM tbl_user
                    WHERE email = 'datphan1@gmail.com');

SET @teacher2_id = (SELECT id
                    FROM tbl_user
                    WHERE email = 'tuanng@gmail.com');

insert into techlearn.teacher (id, name, color, avatar, created_by, created_date, modified_by, modified_date, email)
values  (@teacher2_id, 'TuanLT', '#FFCC33', 'https://i.pinimg.com/564x/1e/d4/4a/1ed44a71d525c0d904a1d52d11beccd0.jpg', null, '2024-09-26 23:15:08', null, '2024-09-26 23:15:08', 'tuanng@gmail.com'),
        (@teacher1_id, 'DatPhan', '#FFCCCC', 'https://i.pinimg.com/564x/1e/d4/4a/1ed44a71d525c0d904a1d52d11beccd0.jpg', null, '2024-09-26 23:14:14', null, '2024-09-26 23:15:27', 'datphan1@gmail.com');


INSERT INTO techlearn.teacher_course (teacher_id, course_id)
VALUES (@teacher1_id, 1),
       (@teacher1_id,2),
       (@teacher2_id,1),
       (@teacher2_id,2);


SET @mentor1_id = (SELECT id
                   FROM tbl_user
                   WHERE email = 'nktuyet1@gmail.com');

SET @mentor2_id = (SELECT id
                   FROM tbl_user
                   WHERE email = 'suongtran@gmail.com');

insert into techlearn.mentor (id, name, avatar, email, color, created_by, created_date, modified_by, modified_date)
values (@mentor1_id, 'TuyetKT', 'https://i.pinimg.com/736x/5b/2d/5c/5b2d5cec5acb5927fcfa2ae36f44d0c2.jpg',
        'nktuyet1@gmail.com', '#CCCCFF', null, '2024-09-26 16:09:03', null, '2024-09-26 16:09:03'),
       (@mentor2_id, 'SuongTran', 'https://i.pinimg.com/736x/5b/2d/5c/5b2d5cec5acb5927fcfa2ae36f44d0c2.jpg',
        'suongtran@gmail.com', '#CCCCFF', null, '2024-09-26 10:19:49', null, '2024-09-26 10:19:49');

insert into  techlearn.mentor_chapter(mentor_id, chapter_id) values (@mentor1_id,1),(@mentor1_id,2),(@mentor2_id,1),(@mentor2_id,2);