DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;
DROP TABLE IF EXISTS groups;
DROP SEQUENCE IF EXISTS group_seq;
DROP TYPE IF EXISTS group_type;
DROP TABLE IF EXISTS projects;
DROP SEQUENCE IF EXISTS project_seq;
DROP TABLE IF EXISTS cities;

CREATE TABLE cities
(
  id        TEXT PRIMARY KEY,
  city_name TEXT NOT NULL
);

CREATE SEQUENCE project_seq
  START 100000;

CREATE TABLE projects
(
  id          INTEGER DEFAULT nextval('project_seq') PRIMARY KEY,
  name        TEXT NOT NULL,
  description TEXT NOT NULL
);

CREATE TYPE group_type AS ENUM ('REGISTERING', 'CURRENT', 'FINISHED');

CREATE SEQUENCE group_seq
  START 100000;

CREATE TABLE groups
(
  id   INTEGER DEFAULT nextval('group_seq') PRIMARY KEY,
  name TEXT       NOT NULL,
  type group_type NOT NULL,
  project_id INTEGER NOT NULL,
  FOREIGN KEY (project_id) REFERENCES public.projects (id)
  ON DELETE CASCADE
);

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE user_seq
  START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT      NOT NULL,
  email     TEXT      NOT NULL,
  flag      user_flag NOT NULL,
  city_id   TEXT      NOT NULL,
  group_id  INTEGER   NOT NULL,
  FOREIGN KEY (city_id) REFERENCES public.cities (id) ON DELETE CASCADE,
  FOREIGN KEY (group_id) REFERENCES public.groups (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX email_idx
  ON users (email);