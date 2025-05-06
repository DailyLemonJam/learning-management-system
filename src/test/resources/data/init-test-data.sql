INSERT INTO course_settings(id, start_date, end_date, is_public)
VALUES('4f8d2b7c-a739-4e9f-94d3-35d289dfc1a6', '2025-01-01 20:00:00', '2025-10-10 20:00:00', true);

INSERT INTO course(id, title, description, price, coins_paid, course_settings_id)
VALUES('b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d', 'Java Course', 'The mose useful description', 50, 250, '4f8d2b7c-a739-4e9f-94d3-35d289dfc1a6');

INSERT INTO student(id, first_name, last_name, email, date_of_birth, coins)
VALUES ('7c5e1f2a-9d84-4b6a-b9d5-6a2f3e7d0c59', 'John', 'Doe', 'john.doe@example.com', '2000-05-06', 100);

INSERT INTO lesson(id, title, duration, course_id)
VALUES ('a48b7f6d-e231-4c98-b7d5-f9e6a4c5831d', 'Introduction to Java', 60, 'b19d4c5f-37a1-4e2b-a7f8-92d5cbefc86d');
