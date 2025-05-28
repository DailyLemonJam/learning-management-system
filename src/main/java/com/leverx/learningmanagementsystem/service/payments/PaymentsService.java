package com.leverx.learningmanagementsystem.service.payments;

import java.util.UUID;

public interface PaymentsService {
    void purchaseCourse(UUID courseId, UUID studentId);
}
