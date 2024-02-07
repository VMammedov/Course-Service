package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.domain.Category;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.response.category.CategoryResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppealResponse sentAppeal(CreateAppealRequest request) {

        AppealResponse appealResponse;

        try {
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
        catch (Exception exception)
        {
            // throw exception
            return null;
        }
    }

    @Override
    public List<AppealResponse> getAllAppeals() {
        try {
            List<Appeal> appeals = appealRepository.findAll();
            List<AppealResponse> appealResponseList = new ArrayList<>();

            for(Appeal appeal : appeals)
            {
                AppealResponse appealResponse = modelMapper.map(appeal, AppealResponse.class);
                appealResponseList.add(appealResponse);
            }

            return appealResponseList;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }

    @Override
    public List<AppealResponse> getAppealsByEmail(String email) {
        try{
            List<Appeal> appeals = appealRepository.findAllByEmail(email);
            List<AppealResponse> appealResponseList = new ArrayList<>();

            for(Appeal appeal : appeals)
            {
                AppealResponse appealResponse = modelMapper.map(appeal, AppealResponse.class);
                appealResponseList.add(appealResponse);
            }

            return appealResponseList;
        } catch (Exception exception)
        {
            // exception
            return null;
        }
    }
}
