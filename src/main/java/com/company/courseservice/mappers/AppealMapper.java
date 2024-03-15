package com.company.courseservice.mappers;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.request.Appeal.CreateAppealRequest;

import com.company.courseservice.response.Appeal.AppealResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppealMapper {
    AppealMapper INSTANCE = Mappers.getMapper(AppealMapper.class);

    Appeal createAppealRequestToAppeal(CreateAppealRequest request);
    AppealResponse appealToAppealResponse(Appeal appeal);

}
