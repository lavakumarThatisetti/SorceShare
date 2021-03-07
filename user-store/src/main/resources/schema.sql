CREATE TABLE IF NOT EXISTS users
(
  id bigserial PRIMARY KEY,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL,
  version INTEGER NOT NULL,
  first_name VARCHAR(40) NOT NULL,
  last_name VARCHAR(40)  NULL ,
  user_name VARCHAR(40) NOT NULL,
  email VARCHAR(40) NOT NULL UNIQUE,
  phone_no VARCHAR(12) UNIQUE,
  dob VARCHAR(20),
  gender VARCHAR(10),
  profession text,
  password text NOT NULL,
  active boolean default true
);

CREATE TABLE IF NOT EXISTS roles
(
  id bigserial PRIMARY KEY,
  name text NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles
(
  user_id bigserial PRIMARY KEY,
  role_id bigserial NOT NULL
);
