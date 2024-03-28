package com.company.courseservice.mappers;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.request.Appeal.CreateAppealRequest;

import com.company.courseservice.response.Appeal.AppealResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppealMapper {
    AppealMapper INSTANCE = Mappers.getMapper(AppealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentDate", ignore = true)
    Appeal createAppealRequestToAppeal(CreateAppealRequest request);

    AppealResponse appealToAppealResponse(Appeal appeal);

}
