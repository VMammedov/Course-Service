package com.company.courseservice.mappers;

import com.company.courseservice.domain.Lecture;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.response.Lecture.LectureResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    LectureMapper INSTANCE = Mappers.getMapper(LectureMapper.class);

    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "section.id", source = "sectionId")
    Lecture createLectureRequestToLecture(CreateLectureRequest request);

    CreateLectureResponse lectureToCreateLectureResponse(Lecture lecture);

    LectureResponse lectureToLectureResponse(Lecture lecture);
}
