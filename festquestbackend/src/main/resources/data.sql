-- Clear and re-insert roles to ensure they exist
DELETE FROM roles WHERE name IN ('CREATOR', 'PARTICIPANT');
INSERT INTO roles (name) VALUES ('CREATOR');
INSERT INTO roles (name) VALUES ('PARTICIPANT');