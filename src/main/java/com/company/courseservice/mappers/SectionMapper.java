package com.company.courseservice.mappers;

import com.company.courseservice.domain.Section;
import com.company.courseservice.request.Section.CreateSectionRequest;
import com.company.courseservice.response.Section.CreatedSectionResponse;
import com.company.courseservice.response.Section.SectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface SectionMapper {

    SectionMapper INSTANCE = Mappers.getMapper(SectionMapper.class);

    Section createSectionRequestToSection(CreateSectionRequest request);
    CreatedSectionResponse sectionToCreateSectionResponse(Section section);
    SectionResponse sectionToSectionResponse(Section section);
}
