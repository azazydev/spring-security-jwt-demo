drop TABLE USER_ROLE;
drop TABLE APP_USER;
drop TABLE APP_Role;

CREATE TABLE APP_USER (
  id          INTEGER PRIMARY KEY,
  user_name VARCHAR(64) NOT NULL,
  arabic_name   VARCHAR(64),
  english_name VARCHAR(64));
  
CREATE TABLE APP_Role (
  id          INTEGER PRIMARY KEY,
  role_code VARCHAR(64) NOT NULL,
  role_desc   VARCHAR(64));
  
CREATE TABLE USER_ROLE (
  user_id INTEGER,
  role_id INTEGER );