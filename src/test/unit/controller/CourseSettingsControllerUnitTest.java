package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.learningmanagementsystem.LearningManagementSystemApplication;
import com.leverx.learningmanagementsystem.course.controller.CourseSettingsController;
import com.leverx.learningmanagementsystem.course.dto.settings.UpdateCourseSettingsRequestDto;
import com.leverx.learningmanagementsystem.course.mapper.CourseSettingsMapper;
import com.leverx.learningmanagementsystem.course.model.Course;
import com.leverx.learningmanagementsystem.course.model.CourseSettings;
import com.leverx.learningmanagementsystem.course.service.settings.CourseSettingsService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseSettingsController.class)
@Import(CourseSettingsMapper.class)
@Tag("Unit")
@ContextConfiguration(classes = LearningManagementSystemApplication.class)
public class CourseSettingsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseSettingsMapper courseSettingsMapper;

    @MockitoBean
    private CourseSettingsService courseSettingsService;

    private final String user = "randomUsername";
    private final String password = "randomPassword";

    @Test
    public void getCourseSettings_givenId_shouldReturnCourseSettingsAnd200() throws Exception {
        // given
        var id = UUID.randomUUID();
        var settings = CourseSettings.builder()
                .id(id)
                .course(new Course())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.of(2025, 8, 25, 20, 0))
                .isPublic(true)
                .build();
        when(courseSettingsService.get(id)).thenReturn(settings);

        // when
        var result = mockMvc.perform(get("/course-settings/{id}", id)
                .with(csrf())
                .with(user(user).password(password)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(id.toString()));
        result.andExpect(jsonPath("$.isPublic").value(true));
    }

    @Test
    public void updateCourseSettings_givenIdAndRequestDto_shouldUpdateAndReturnCourseSettingsAnd200() throws Exception {
        // given
        var id = UUID.randomUUID();
        var request = new UpdateCourseSettingsRequestDto(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(8),
                false
        );
        var updateRequestModel = courseSettingsMapper.toModel(request);
        var updatedSettings = CourseSettings.builder()
                .id(id)
                .course(new Course())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .isPublic(request.isPublic())
                .build();
        when(courseSettingsService.update(id, updateRequestModel)).thenReturn(updatedSettings);

        // when
        var result = mockMvc.perform(put("/course-settings/{id}", id)
                .with(csrf())
                .with(user(user).password(password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSettings)));

        // then
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(id.toString()));
        result.andExpect(jsonPath("$.isPublic").value(false));
    }

}
