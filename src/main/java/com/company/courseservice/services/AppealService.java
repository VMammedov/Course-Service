package com.company.courseservice.services;

import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;

import java.util.List;

public interface AppealService {
    AppealResponse sentAppeal(CreateAppealRequest request);

    List<AppealResponse> getAllAppeals();

    List<AppealResponse> getAppealsByEmail(String email);
    AppealResponse getAppeal(Long id);
}
