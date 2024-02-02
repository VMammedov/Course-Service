package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.SentMessageRequest;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AppealRepository repository;

    @Override
    public Appeal sentAppeal(SentMessageRequest request) {
        Appeal appeal = Appeal.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .message(request.getMessage())
                .sentDate(new Date())
                .build();
        return repository.save(appeal);
    }

    @Override
    public List<Appeal> getAllAppeals() {
        return repository.findAll();
    }

    @Override
    public Appeal getAppealByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(()->
                new RuntimeException("Appeal not found by email"));
    }
}
