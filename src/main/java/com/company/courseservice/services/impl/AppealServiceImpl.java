package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppealResponse sentAppeal(CreateAppealRequest request) {

        AppealResponse appealResponse;

        Appeal appeal = Appeal.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .message(request.getMessage())
                .sentDate(new Date())
                .build();

        appeal = appealRepository.save(appeal);

        appealResponse = modelMapper.map(appeal, AppealResponse.class);

        return appealResponse;
    }

    @Override
    public List<AppealResponse> getAllAppeals() {
        List<AppealResponse> appealResponseList;
        List<Appeal> appeals = appealRepository.findAll();

        appealResponseList = appeals.stream().map((appeal) -> modelMapper.map(appeal, AppealResponse.class)).collect(Collectors.toList());

        return appealResponseList;
    }

    @Override
    public List<AppealResponse> getAppealsByEmail(String email) {
        List<AppealResponse> appealResponseList;
        List<Appeal> appeals = appealRepository.findAllByEmail(email);
      
        appealResponseList = appeals.stream().map((appeal -> modelMapper.map(appeal, AppealResponse.class))).collect(Collectors.toList());
      
        return appealResponseList;
    }

    @Override
    public AppealResponse getAppeal(Long id) {
        AppealResponse appealResponse;

        Optional<Appeal> appeal = appealRepository.findById(id);

        appealResponse = modelMapper.map(appeal.orElseThrow(
                () -> new DataNotFoundException("Appeal with " + id + " id not found.")), AppealResponse.class);

        return appealResponse;
    }
}
