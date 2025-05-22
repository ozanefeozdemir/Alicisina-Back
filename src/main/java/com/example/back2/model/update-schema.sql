CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NULL,
    phone      VARCHAR(255) NULL,
    address    VARCHAR(255) NULL,
    `role`     VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT `uc_users_emaıl` UNIQUE (email);
CREATE TABLE product
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)          NULL,
    description  VARCHAR(255)          NULL,
    price        DOUBLE                NULL,
    image_url    VARCHAR(255)          NULL,
    user_email   VARCHAR(255)          NULL,
    category     VARCHAR(255)          NULL,
    sub_category VARCHAR(255)          NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);