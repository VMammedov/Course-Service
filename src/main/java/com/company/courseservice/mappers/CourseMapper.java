package com.company.courseservice.mappers;

import com.company.courseservice.domain.Course;
import com.company.courseservice.response.Course.CourseResponse;
import com.company.courseservice.response.Course.CreateCourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);


    CreateCourseResponse courseToCreateCourseResponse(Course course);

    CourseResponse courseToCourseResponse(Course course);
}
