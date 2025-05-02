package com.leverx.learningmanagementsystem.service.payments;

import com.leverx.learningmanagementsystem.dto.payment.PurchaseCourseRequest;

import java.util.UUID;

public interface PaymentsService {
    void purchaseCourse(UUID courseId, PurchaseCourseRequest request);
}
