CREATE TABLE tbl_currency
(
    id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime NULL,
    modified_date datetime NULL,
    modified_by   VARCHAR(255) NULL,
    units          VARCHAR(255) NULL,
    is_deleted    BIT(1) NULL
);

CREATE TABLE tbl_points
(
    id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime NULL,
    modified_date datetime NULL,
    modified_by   VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    points        INT NULL,
    price         DECIMAL(10, 2) NULL,
    id_currency   INT NULL,
    is_deleted    BIT(1) NULL,
    CONSTRAINT fk_currency FOREIGN KEY (id_currency) REFERENCES tbl_currency(id)
);
CREATE TABLE tbl_student_point
(
    id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255) NULL,
    created_date  datetime NULL,
    modified_date datetime NULL,
    modified_by   VARCHAR(255) NULL,
    id_points   INT NULL,
    id_user       BINARY(16)            NULL,
    is_deleted    BIT(1) NULL,
    CONSTRAINT fk_points FOREIGN KEY (id_points) REFERENCES tbl_points(id),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES tbl_user(id)
);
INSERT INTO tbl_currency (created_by, created_date, modified_date, modified_by, units, is_deleted)
VALUES
    ('admin', NOW(), NOW(), 'admin', 'USD', 0),
    ('admin', NOW(), NOW(), 'admin', 'EUR', 0),
    ('admin', NOW(), NOW(), 'admin', 'VND', 0);

INSERT INTO tbl_points (name, points, price, id_currency, is_deleted)
VALUES
    ('Gói 1', 100, 30.00, 2, 0),
    ('Gói 2', 50, 10.00, 1, 0),
    ('Gói 3', 30, 700000.00, 3, 0);

SET @vi_tran_id = (SELECT id FROM tbl_user WHERE email = 'tieuvi200904@gmail.com');
INSERT INTO tbl_student_point (created_by, created_date, modified_date, modified_by, id_points, id_user, is_deleted)
VALUES
    ('admin', NOW(), NULL, NULL, 1,  @vi_tran_id, 0),
    ('admin', NOW(), NULL, NULL, 2,  @vi_tran_id, 0),
    ('admin', NOW(), NULL, NULL, 3,  @vi_tran_id, 0);



