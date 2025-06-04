package com.leverx.learningmanagementsystem.lesson.common.builder;

import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.lesson.model.ClassroomLesson;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;

public class LessonBuilder {

    public static Lesson createVideoLesson(Course course) {
        return VideoLesson.builder()
                .title("Test video lesson")
                .platform("google")
                .duration(90)
                .course(course)
                .build();
    }

    public static Lesson createClassroomLesson(Course course) {
        return ClassroomLesson.builder()
                .title("Test classroom lesson")
                .capacity(30)
                .location("London")
                .duration(120)
                .course(course)
                .build();
    }
}
