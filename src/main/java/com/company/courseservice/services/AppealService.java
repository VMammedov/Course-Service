package com.company.courseservice.services;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.request.SentMessageRequest;

import java.util.List;

public interface AppealService {
    Appeal sentAppeal(SentMessageRequest request);

    List<Appeal> getAllAppeals();

    Appeal getAppealByEmail(String email);
}
