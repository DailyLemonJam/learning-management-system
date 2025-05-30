package com.leverx.learningmanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.course.dto.CreateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.UpdateCourseRequestDto;
import com.leverx.learningmanagementsystem.course.dto.settings.CreateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.security.role.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("Integration")
public class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

    @BeforeEach
    public void setUp() {
        courseRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    public void createCourse_givenCreateCourseRequestDto_shouldReturnCourseAndReturn201() throws Exception {
        // given
        var createCourseSettingsRequestDto = new CreateCourseSettingsRequestDto(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                true
        );
        var createCourseRequestDto = new CreateCourseRequestDto("New Course", "Description for course",
                BigDecimal.valueOf(100), createCourseSettingsRequestDto);

        // when
        var result = mockMvc.perform(post("/courses")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseRequestDto)));
        var getResult = mockMvc.perform(get("/courses")
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCourseRequestDto)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.title").value("New Course"));
        result.andExpect(jsonPath("$.description").value("Description for course"));
        result.andExpect(jsonPath("$.courseSettings.isPublic").value(true));

        getResult.andExpect(status().isOk());
        getResult.andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    public void getCourse_givenId_shouldReturnCourseAndReturn200() throws Exception {
        // given
        var courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        course.getCourseSettings().setCourse(course);
        var savedCourse = courseRepository.save(course);

        // when
        var result = mockMvc.perform(get("/courses/{id}", savedCourse.getId())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("Java Course"));
        result.andExpect(jsonPath("$.description").value("The most useful description"));
        result.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(50)));
    }

    @Test
    public void getAllCourses_shouldReturnAllCoursesAndReturn200() throws Exception {
        // given
        var courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        course.getCourseSettings().setCourse(course);
        var courseSettings2 = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course2 = Course.builder()
                .title("Java Course 2")
                .courseSettings(courseSettings2)
                .description("The most useful description 2")
                .price(BigDecimal.valueOf(100))
                .coinsPaid(BigDecimal.valueOf(500))
                .build();
        course2.getCourseSettings().setCourse(course2);
        courseRepository.save(course);
        courseRepository.save(course2);

        // when
        var result = mockMvc.perform(get("/courses")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(2));
        result.andExpect(jsonPath("$.content[0].title").value("Java Course"));
        result.andExpect(jsonPath("$.content[1].price").value(BigDecimal.valueOf(100)));
    }

    @Test
    public void updateCourse_givenUpdateCourseRequestDto_shouldUpdateCourseAndReturn200() throws Exception {
        // given
        var courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        course.getCourseSettings().setCourse(course);
        var savedCourse = courseRepository.save(course);

        var updateRequest = new UpdateCourseRequestDto("New Nice Course Title", "New description", BigDecimal.valueOf(60));

        // when
        var result = mockMvc.perform(put("/courses/{id}", savedCourse.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.title").value("New Nice Course Title"));
        result.andExpect(jsonPath("$.description").value("New description"));
        result.andExpect(jsonPath("$.price").value(BigDecimal.valueOf(60)));
        result.andExpect(jsonPath("$.id").value(savedCourse.getId().toString()));
    }

    @Test
    public void deleteCourse_givenId_shouldDeleteCourseAndReturn204AndReturn404() throws Exception {
        // given
        var courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .endDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        var course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        course.getCourseSettings().setCourse(course);
        var savedCourse = courseRepository.save(course);

        // when
        var result = mockMvc.perform(delete("/courses/{id}", savedCourse.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));
        var deleteResult = mockMvc.perform(delete("/courses/{id}", savedCourse.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNoContent());
        deleteResult.andExpect(status().isNotFound());
    }

}
