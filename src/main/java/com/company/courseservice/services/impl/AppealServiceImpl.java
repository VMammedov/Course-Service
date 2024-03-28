package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.mappers.AppealMapper;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealListResponse;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import utils.PaginationInfo;
import utils.PaginationUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    @Override
    public AppealResponse sentAppeal(CreateAppealRequest request) {

        Appeal appeal = appealRepository.save(AppealMapper.INSTANCE.createAppealRequestToAppeal(request));
        return AppealMapper.INSTANCE.appealToAppealResponse(appealRepository.save(appeal));
    }

    @Override
    public AppealListResponse getAllAppeals(Pageable pageable) {

        AppealListResponse response = AppealListResponse.builder().build();
        Page<Appeal> appeals = appealRepository.findAll(pageable);
        response.setItems(appeals.getContent().stream().map(AppealMapper.INSTANCE::appealToAppealResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(appeals));
        return response;
    }

    @Override
    public AppealListResponse getAppealsByEmail(String email, Pageable pageable) {

        AppealListResponse response = AppealListResponse.builder().build();
        Page<Appeal> appeals = appealRepository.findAllByEmailContains(email, pageable);
        response.setItems(appeals.getContent().stream().map(AppealMapper.INSTANCE::appealToAppealResponse).collect(Collectors.toList()));
        response.setPaginationInfo(PaginationUtil.getPaginationInfo(appeals));
        return response;
    }

    @Override
    public AppealResponse getAppeal(Long id) {

        Optional<Appeal> appeal = appealRepository.findById(id);

        if(appeal.isEmpty())
            throw new DataNotFoundException("Appeal not found by "+id+" id");

        return AppealMapper.INSTANCE.appealToAppealResponse(appeal.get());
    }
}
