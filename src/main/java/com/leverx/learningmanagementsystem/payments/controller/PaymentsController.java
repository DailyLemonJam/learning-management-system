package com.leverx.learningmanagementsystem.payments.controller;

import com.leverx.learningmanagementsystem.payments.dto.PurchaseCourseRequestDto;
import com.leverx.learningmanagementsystem.payments.service.PaymentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments Controller", description = "Handles Payments operations")
public class PaymentsController {
    private final PaymentsService paymentsService;

    @PostMapping("/purchase-course/{courseId}")
    @ResponseStatus(OK)
    @Operation(summary = "Purchase Course", description = "Connects Student and specified Course, handles necessary transactions")
    public void purchaseCourse(@PathVariable UUID courseId,
                               @Valid @RequestBody PurchaseCourseRequestDto request) {
        paymentsService.purchaseCourse(courseId, request.studentId());
    }

}
