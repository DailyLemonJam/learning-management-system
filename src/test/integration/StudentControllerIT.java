import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.LearningManagementSystemApplication;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.learningmanagementsystem.lesson.model.Lesson;
import com.leverx.learningmanagementsystem.lesson.model.VideoLesson;
import com.leverx.learningmanagementsystem.lesson.repository.LessonRepository;
import com.leverx.learningmanagementsystem.security.role.Role;
import com.leverx.learningmanagementsystem.student.dto.CreateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.dto.UpdateStudentRequestDto;
import com.leverx.learningmanagementsystem.student.model.Language;
import com.leverx.learningmanagementsystem.student.model.Student;
import com.leverx.learningmanagementsystem.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LearningManagementSystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("Integration")
class StudentControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

    private Course course;
    private CourseSettings courseSettings;
    private Student student;
    private Lesson videoLesson;

    @BeforeEach
    public void setUp() {
        course = Course.builder()
                .title("Java Course")
                .courseSettings(courseSettings)
                .description("The most useful description")
                .price(BigDecimal.valueOf(50))
                .coinsPaid(BigDecimal.valueOf(250))
                .build();
        courseSettings = CourseSettings.builder()
                .startDate(LocalDateTime.now().plusDays(25))
                .startDate(LocalDateTime.now().plusDays(50))
                .isPublic(true)
                .build();
        student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email(UUID.randomUUID() + "john.doe@example.com")
                .dateOfBirth(LocalDate.of(2000, 5, 8))
                .coins(BigDecimal.valueOf(50))
                .courses(new ArrayList<>())
                .build();
        videoLesson = VideoLesson.builder()
                .course(course)
                .title("Video lesson #1")
                .duration(60)
                .course(course)
                .url("url.of.this.lesson")
                .platform("Zoom")
                .build();
        courseRepository.save(course);
        studentRepository.save(student);
        lessonRepository.save(videoLesson);
    }

    @Test
    @Sql(scripts = "/data/clear-db.sql")
    public void createStudent_givenCreateStudentRequestDto_shouldReturnStudentAndReturn200() throws Exception {
        // given
        var requestDto = new CreateStudentRequestDto("John", "Johnson",
                "validemail@email.com", LocalDate.now().minusDays(365 * 25),
                Language.ENGLISH);

        // when
        var result = mockMvc.perform(post("/students")
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Johnson"));
        result.andExpect(jsonPath("$.coins").value(new BigDecimal(0)));

        var result2 = mockMvc.perform(get("/students")
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        result2.andExpect(status().isOk());
        result2.andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @Sql(scripts = "/data/clear-db.sql")
    public void getStudent_givenId_shouldReturnStudentAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/students/{id}", student.getId())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value("John"));
        result.andExpect(jsonPath("$.lastName").value("Doe"));
        result.andExpect(jsonPath("$.dateOfBirth").value(LocalDate.of(2000, 5, 8).toString()));
        result.andExpect(jsonPath("$.coins").value(BigDecimal.valueOf(50)));
    }

    @Test
    @Sql(scripts = "/data/clear-db.sql")
    public void getAllStudents_shouldReturnAllStudentsAndReturn200() throws Exception {
        // when
        var result = mockMvc.perform(get("/students")
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    @Sql(scripts = "/data/clear-db.sql")
    public void updateStudent_givenUpdateStudentRequestDto_shouldUpdateStudentAndReturn200() throws Exception {
        // given
        var localDate = LocalDate.now().minusDays(365 * 24);
        var request = new UpdateStudentRequestDto("New not last name", "New not first name",
                "email@email.com", localDate, Language.ENGLISH);

        // when
        var result = mockMvc.perform(put("/students/{id}", student.getId())
                        .with(csrf())
                        .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value("New not last name"));
        result.andExpect(jsonPath("$.lastName").value("New not first name"));
        result.andExpect(jsonPath("$.email").value("email@email.com"));
        result.andExpect(jsonPath("$.dateOfBirth").value(localDate.toString()));
    }

    @Test
    @Sql(scripts = "/data/clear-db.sql")
    public void deleteStudent_givenId_shouldDeleteStudentAndReturn204AndReturn404() throws Exception {
        // when
        var result = mockMvc.perform(delete("/students/{id}", student.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNoContent());

        // when
        result = mockMvc.perform(delete("/students/{id}", student.getId())
                .with(csrf())
                .with(user(defaultUserUsername).password(defaultUserPassword).roles(Role.USER.getValue())));

        // then
        result.andExpect(status().isNotFound());
    }

}
