package com.company.courseservice.mappers;

import com.company.courseservice.domain.Course;
import com.company.courseservice.response.Course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseResponse courseToCourseResponse(Course course);
}
