CREATE TABLE IF NOT EXISTS user_role (
    id BIGINT NOT NULL CONSTRAINT user_role_pkey PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS department (
    id BIGINT NOT NULL CONSTRAINT department_pkey PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    active BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT NOT NULL CONSTRAINT user_pkey PRIMARY KEY,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(256) NOT NULL,
    full_name VARCHAR(256) NOT NULL,
    role_id BIGINT CONSTRAINT fk_user_role_id REFERENCES user_role,
    email VARCHAR(128),
    department_id BIGINT CONSTRAINT fk_user_department_id REFERENCES department,
    birth_day TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT FALSE NOT NULL,
    update_date TIMESTAMP NOT NULL
);