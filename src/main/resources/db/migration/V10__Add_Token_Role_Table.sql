CREATE TABLE tbl_roles
(
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_tbl_roles PRIMARY KEY (id)
);

CREATE TABLE tbl_user_roles
(
    user BINARY(16) NOT NULL,
    role INT NOT NULL,
    PRIMARY KEY (user, role),
    FOREIGN KEY (user) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role) REFERENCES tbl_roles(id) ON DELETE CASCADE
);

CREATE TABLE tbl_token
(
    id INT AUTO_INCREMENT NOT NULL,
    token VARCHAR(255) NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expired BOOLEAN NOT NULL,
    revoked BOOLEAN NOT NULL,
    user_id BINARY(16),
    CONSTRAINT pk_tbl_token PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE
);

ALTER TABLE tbl_user
ADD COLUMN email VARCHAR(255) NOT NULL,
ADD COLUMN password VARCHAR(255) NULL;
