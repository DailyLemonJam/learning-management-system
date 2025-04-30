CREATE TABLE lesson (
    id UUID PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    duration INT NOT NULL,
    course_id UUID NOT NULL
);

ALTER TABLE lesson
ADD CONSTRAINT fk_lesson_course
FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE;
