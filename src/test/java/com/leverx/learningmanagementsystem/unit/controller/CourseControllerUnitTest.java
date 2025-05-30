package com.leverx.learningmanagementsystem.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.course.controller.CourseController;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.mapper.CourseMapper;
import com.leverx.learningmanagementsystem.course.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.service.CourseService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@Import({CourseMapper.class, CourseSettingsMapper.class})
@Tag("Unit")
public class CourseControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseMapper courseMapper;

    @MockitoBean
    private CourseService courseService;

    private final String user = "randomUsername";
    private final String password = "randomPassword";

    @Test
    public void createCourse_givenCreateCourseRequestDto_shouldCreateCourseAndReturn201() throws Exception {
        // given
        var settings = new CreateCourseSettingsRequestDto(LocalDateTime.now(), LocalDateTime.now().plusDays(1), true);
        var request = new CreateCourseRequestDto("title", "description",
                new BigDecimal(250), settings);
        var course = courseMapper.toModel(request);
        when(courseService.create(course)).thenReturn(course);

        // when
        var result = mockMvc.perform(post("/courses")
                .with(csrf())
                .with(user(user).password(password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.title").value("title"));
        result.andExpect(jsonPath("$.description").value("description"));
        result.andExpect(jsonPath("$.price").value(new BigDecimal(250)));
    }

    @Test
    public void getCourse_givenId_shouldReturnCourseAndReturn200() throws Exception {
        // given
        var id = UUID.randomUUID();
        var course = Course.builder()
                .id(id)
                .courseSettings(CourseSettings.builder().build())
                .description("Course description")
                .price(BigDecimal.valueOf(60))
                .lessons(new ArrayList<>())
                .build();
        when(courseService.get(id)).thenReturn(course);

        // when
        var result = mockMvc.perform(get("/courses/{id}", id)
                .with(csrf())
                .with(user(user).password(password)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(id.toString()));
        result.andExpect(jsonPath("$.price").value(new BigDecimal(60)));
        result.andExpect(jsonPath("$.description").value("Course description"));
    }

    @Test
    public void getAllCourses_shouldReturnAllCoursesAndReturn200() throws Exception {
        // given
        var pageable = PageRequest.of(0, 5);
        var courses = List.of(
                Course.builder()
                        .courseSettings(CourseSettings.builder().build())
                        .description("Course")
                        .price(BigDecimal.valueOf(60))
                        .lessons(new ArrayList<>())
                        .build(),
                Course.builder()
                        .courseSettings(CourseSettings.builder().build())
                        .description("Course 2")
                        .price(BigDecimal.valueOf(80))
                        .lessons(new ArrayList<>())
                        .build()
        );
        var coursePage = new PageImpl<>(courses, pageable, courses.size());
        when(courseService.getAll(any(Pageable.class))).thenReturn(coursePage);

        // when
        var result = mockMvc.perform(get("/courses")
                .with(csrf())
                .with(user(user).password(password)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(2));
        result.andExpect(jsonPath("$.content[0].description").value("Course"));
        result.andExpect(jsonPath("$.content[1].price").value(BigDecimal.valueOf(80)));
    }

    @Test
    public void updateCourse_givenUpdateCourseRequestDto_shouldUpdateCourseAndReturn200() throws Exception {
        // given
        var id = UUID.randomUUID();
        var request = new UpdateCourseRequestDto("New Title", "New Description", BigDecimal.valueOf(20));
        var updateRequestModel = courseMapper.toModel(request);
        var updatedCourse = Course.builder()
                .id(id)
                .title("New Title")
                .description("New Description")
                .courseSettings(CourseSettings.builder().build())
                .price(BigDecimal.valueOf(20))
                .lessons(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
        when(courseService.update(id, updateRequestModel)).thenReturn(updatedCourse);

        // when
        var result = mockMvc.perform(put("/courses/{id}", id)
                .with(csrf())
                .with(user(user).password(password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("New Title"));
        result.andExpect(jsonPath("$.description").value("New Description"));
        result.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(20)));
    }

    @Test
    public void deleteCourse_givenId_shouldDeleteCourseAndReturn204() throws Exception {
        // given
        var id = UUID.randomUUID();
        doNothing().when(courseService).delete(id);

        // when
        var result = mockMvc.perform(delete("/courses/{id}", id)
                .with(csrf())
                .with(user(user).password(password)));

        // then
        result.andExpect(status().isNoContent());
    }

}
