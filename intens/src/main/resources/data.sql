INSERT INTO skill (id, name) VALUES (1, 'Java');
INSERT INTO skill (id, name) VALUES (2, 'Spring Boot');
INSERT INTO skill (id, name) VALUES (3, 'C#');
INSERT INTO skill (id, name) VALUES (4, '.NET');
INSERT INTO skill (id, name) VALUES (5, 'SQL');
INSERT INTO skill (id, name) VALUES (6, 'Docker');
INSERT INTO skill (id, name) VALUES (7, 'JavaScript');
INSERT INTO skill (id, name) VALUES (8, 'React');
INSERT INTO skill (id, name) VALUES (9, 'English');
INSERT INTO skill (id, name) VALUES (10, 'German');

INSERT INTO candidate (id, first_name, last_name, birth_date, phone_number, email)
VALUES (1, 'Lionel', 'Messi', '1987-06-24', '061123456', 'messi@gmail.com');

INSERT INTO candidate (id, first_name, last_name, birth_date, phone_number, email)
VALUES (2, 'Cristiano', 'Ronaldo', '1985-02-05', '062234567', 'ronaldo@gmail.com');

INSERT INTO candidate (id, first_name, last_name, birth_date, phone_number, email)
VALUES (3, 'Kylian', 'Mbappe', '1998-12-20', '063345678', 'mbappe@gmail.com');

INSERT INTO candidate (id, first_name, last_name, birth_date, phone_number, email)
VALUES (4, 'Erling', 'Haaland', '2000-07-21', '064456789', 'haaland@gmail.com');

INSERT INTO candidate (id, first_name, last_name, birth_date, phone_number, email)
VALUES (5, 'Lamine', 'Yamal', '2007-07-13', '065567890', 'yamal@gmail.com');

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 1);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 2);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (1, 4);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 3);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 4);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (2, 9);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (3, 7);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (3, 8);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (3, 9);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (4, 1);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (4, 5);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (4, 6);

INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (5, 2);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (5, 4);
INSERT INTO candidate_skill (candidate_id, skill_id) VALUES (5, 10);