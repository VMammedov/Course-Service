package com.company.courseservice.services;

import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealListResponse;
import com.company.courseservice.response.Appeal.AppealResponse;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AppealService {
    AppealResponse sentAppeal(CreateAppealRequest request);

    AppealListResponse getAllAppeals(Pageable pageable);

    AppealListResponse getAppealsByEmail(String email, Pageable pageable);

    AppealResponse getAppeal(Long id);
}
