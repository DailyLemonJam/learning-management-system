CREATE TABLE student_course (
    student_id UUID NOT NULL,
    course_id UUID NOT NULL,
    PRIMARY KEY (student_id, course_id)
);

ALTER TABLE student_course
ADD CONSTRAINT fk_student_course_student
FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE;

ALTER TABLE student_course
ADD CONSTRAINT fk_student_course_course
FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE;
