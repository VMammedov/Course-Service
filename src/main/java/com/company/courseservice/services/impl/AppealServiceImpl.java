package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.mappers.AppealMapper;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    @Override
    public AppealResponse sentAppeal(CreateAppealRequest request) {
        Appeal appeal = appealRepository.save(AppealMapper.INSTANCE.createAppealRequestToAppeal(request));
        return AppealMapper.INSTANCE.appealToAppealResponse(appeal);
    }

    @Override
    public List<AppealResponse> getAllAppeals() {
        List<Appeal> appeals = appealRepository.findAll();
        return appeals.stream().map(AppealMapper.INSTANCE::appealToAppealResponse).collect(Collectors.toList());
    }

    @Override
    public List<AppealResponse> getAppealsByEmail(String email) {
        List<Appeal> appeals = appealRepository.findAllByEmail(email);

        return appeals.stream().map(AppealMapper.INSTANCE::appealToAppealResponse).collect(Collectors.toList());
    }

    @Override
    public AppealResponse getAppeal(Long id) {

        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Appeal not found by "+id+" id"));
        return AppealMapper.INSTANCE.appealToAppealResponse(appeal);
    }
}
