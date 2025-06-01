package com.leverx.learningmanagementsystem.lesson.model;

import lombok.Getter;

public enum LessonType {

    CLASSROOM("CLASSROOM"), VIDEO("VIDEO");

    @Getter
    private final String name;

    LessonType(String type) {
        this.name = type;
    }

}
