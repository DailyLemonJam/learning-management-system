package com.leverx.learningmanagementsystem.controller;

import com.leverx.learningmanagementsystem.dto.payment.PurchaseCourseRequest;
import com.leverx.learningmanagementsystem.service.payments.PaymentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {
    private final PaymentsService paymentsService;

    @PostMapping("/purchase-course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addCourse(@PathVariable UUID id,
                                            @Valid @RequestBody PurchaseCourseRequest request) {
        paymentsService.purchaseCourse(id, request);
    }

}
