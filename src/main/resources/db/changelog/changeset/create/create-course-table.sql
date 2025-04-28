CREATE TABLE course (
    id UUID PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC NOT NULL,
    coins_paid NUMERIC NOT NULL,
    course_settings_id UUID NOT NULL
);

ALTER TABLE course
ADD CONSTRAINT fk_course_settings
FOREIGN KEY (course_settings_id) REFERENCES course_settings (id);
