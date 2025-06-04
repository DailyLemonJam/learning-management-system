package com.leverx.learningmanagementsystem.payments.service;

import java.util.UUID;

public interface PaymentsService {

    void purchaseCourse(UUID courseId, UUID studentId);
}
