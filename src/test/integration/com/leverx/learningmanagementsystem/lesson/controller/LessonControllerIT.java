package com.leverx.learningmanagementsystem.lesson.controller;

import com.leverx.learningmanagementsystem.AbstractCommonIT;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.lesson.dto.CreateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.dto.UpdateLessonRequestDto;
import com.leverx.learningmanagementsystem.lesson.repository.LessonRepository;
import com.leverx.learningmanagementsystem.util.CourseUtilIT;
import com.leverx.learningmanagementsystem.util.LessonUtilIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LessonControllerIT extends AbstractCommonIT {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        lessonRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void createVideoLesson_givenCreateLessonRequestDto_shouldReturnCourseAndReturn201() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);
        var createLessonRequest = new CreateLessonRequestDto("First video lesson", 60, "VIDEO",
                null, null, "video.url.com", "Zoom", savedCourse.getId());

        // when
        var result = mockMvc.perform(post("/lessons")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLessonRequest)));
        var getResult = mockMvc.perform(get("/lessons"));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.title").value("First video lesson"));
        result.andExpect(jsonPath("$.duration").value(60));
        result.andExpect(jsonPath("$.lessonType").value("VIDEO"));
        result.andExpect(jsonPath("$.url").value("video.url.com"));
        result.andExpect(jsonPath("$.platform").value("Zoom"));
        result.andExpect(jsonPath("$.courseId").value(savedCourse.getId().toString()));

        getResult.andExpect(status().isOk());
        getResult.andExpect(jsonPath("$.content.length()").value(1));
        getResult.andExpect(jsonPath("$.content[0].lessonType").value("VIDEO"));
    }

    @Test
    @WithMockUser
    public void getVideoLesson_givenId_shouldReturnLessonAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        var lesson = LessonUtilIT.createVideoLesson(course);
        var savedLesson = lessonRepository.save(lesson);

        // when
        var result = mockMvc.perform(get("/lessons/{id}", savedLesson.getId()));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("Test video lesson"));
        result.andExpect(jsonPath("$.duration").value(90));
        result.andExpect(jsonPath("$.courseId").value(savedCourse.getId().toString()));
    }

    @Test
    @WithMockUser
    public void getAllLessons_shouldReturnAllLessonsAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        var videoLesson = LessonUtilIT.createVideoLesson(savedCourse);
        var classroomLesson = LessonUtilIT.createClassroomLesson(savedCourse);
        lessonRepository.save(videoLesson);
        lessonRepository.save(classroomLesson);

        // when
        var result = mockMvc.perform(get("/lessons")
                .param("sort", "duration,asc"));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(2));
        result.andExpect(jsonPath("$.content[0].lessonType").value("VIDEO"));
        result.andExpect(jsonPath("$.content[0].title").value("Test video lesson"));
        result.andExpect(jsonPath("$.content[0].platform").value("google"));
        result.andExpect(jsonPath("$.content[0].duration").value(90));
        result.andExpect(jsonPath("$.content[1].lessonType").value("CLASSROOM"));
        result.andExpect(jsonPath("$.content[1].title").value("Test classroom lesson"));
        result.andExpect(jsonPath("$.content[1].capacity").value(30));
        result.andExpect(jsonPath("$.content[1].location").value("London"));
    }

    @Test
    @WithMockUser
    public void updateVideoLesson_givenUpdateLessonRequestDto_shouldUpdateLessonAndReturn200() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        var videoLesson = LessonUtilIT.createVideoLesson(savedCourse);
        var savedVideoLesson = lessonRepository.save(videoLesson);

        var updateVideoLessonRequest = new UpdateLessonRequestDto("New Nice Course Title", 75, "VIDEO",
                null, null, "url.com", "Google");

        // when
        var result = mockMvc.perform(put("/lessons/{id}", savedVideoLesson.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateVideoLessonRequest)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("New Nice Course Title"));
        result.andExpect(jsonPath("$.lessonType").value("VIDEO"));
        result.andExpect(jsonPath("$.url").value("url.com"));
        result.andExpect(jsonPath("$.platform").value("Google"));
    }

    @Test
    @WithMockUser
    public void deleteLesson_givenId_shouldDeleteLessonAndReturn204AndReturn404() throws Exception {
        // given
        var course = CourseUtilIT.createCourse();
        var savedCourse = courseRepository.save(course);

        var videoLesson = LessonUtilIT.createVideoLesson(savedCourse);
        var savedVideoLesson = lessonRepository.save(videoLesson);

        // when
        var result = mockMvc.perform(delete("/lessons/{id}", savedVideoLesson.getId())
                .with(csrf()));
        var deleteResult = mockMvc.perform(delete("/lessons/{id}", savedVideoLesson.getId())
                .with(csrf()));

        // then
        result.andExpect(status().isNoContent());
        deleteResult.andExpect(status().isNotFound());
    }
}
