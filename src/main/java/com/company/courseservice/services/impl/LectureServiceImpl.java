package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Lecture;
import com.company.courseservice.dto.CreateBulkLectureDto;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.LectureMapper;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.LectureRepository;
import com.company.courseservice.request.Lecture.CreateBulkLectureRequest;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.request.Lecture.UpdateLectureRequest;
import com.company.courseservice.response.Lecture.CreateBulkLectureResponse;
import com.company.courseservice.response.Lecture.CreateLectureResponse;
import com.company.courseservice.response.Lecture.LectureListResponse;
import com.company.courseservice.response.Lecture.LectureResponse;
import com.company.courseservice.services.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.AuthUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public CreateLectureResponse createLecture(CreateLectureRequest request) {
        Long courseId = request.getCourseId();
        Long sectionId = request.getSectionId();
        String userEmail = AuthUtil.getCurrentUserEmail();

        boolean isCourseAndSectionValid = courseRepository.existsCourseAndSectionForUser(courseId, sectionId, userEmail);

        if (isCourseAndSectionValid) {
            Lecture lecture = lectureRepository.save(LectureMapper.INSTANCE.createLectureRequestToLecture(request));
            return LectureMapper.INSTANCE.lectureToCreateLectureResponse(lecture);
        } else {
            throw new IllegalRequestException("Course not found for this user with that section!");
        }
    }

    @Override
    @Transactional
    public CreateBulkLectureResponse createBulkLecture(CreateBulkLectureRequest request) {

        Long courseId = request.getCourseId();
        String currentUserEmail = AuthUtil.getCurrentUserEmail();
        List<Lecture> lectures = new ArrayList<>();
        Set<Long> sectionIds = new HashSet<>();

        boolean isCourseBelongUser = courseRepository.existsCourseForUser(
                courseId,
                currentUserEmail
                );

        if(!isCourseBelongUser) {
            throw new IllegalRequestException("Course does not belong to the user!");
        }

        for (CreateBulkLectureDto item : request.getLectures()) {
            Long sectionId = item.getSectionId();

            if (!sectionIds.contains(sectionId)) {
                sectionIds.add(sectionId);

                if (!courseRepository.existsSectionForCourse(courseId, sectionId)) {
                    throw new IllegalRequestException("Section does not belong to the course!");
                }
            }
            lectures.add(LectureMapper.INSTANCE.createBulkLectureDtoToLecture(item, courseId));
        }
        List<Lecture> savedLectures = lectureRepository.saveAll(lectures);

        List<CreateLectureResponse> lectureResponses = savedLectures.stream().map(LectureMapper.INSTANCE :: lectureToCreateLectureResponse).collect(Collectors.toList());

        return CreateBulkLectureResponse.builder().lectures(lectureResponses).courseId(courseId).build();
    }

    @Override
    public LectureResponse getLectureById(Long id) {
        Lecture lecture = findLectureById(id);
        return LectureMapper.INSTANCE.lectureToLectureResponse(lecture);
    }

    @Override
    public LectureListResponse getLectureList(Long courseId, Long sectionId) {

        LectureListResponse lectureListResponse = new LectureListResponse();
        lectureListResponse.setSectionId(sectionId);

        if (!courseRepository.existsSectionForCourse(courseId, sectionId))
            throw new IllegalRequestException("Section does not belong to the course!");

        lectureRepository.findAllByCourseIdAndSectionId(courseId, sectionId)
                .forEach(lecture ->  lectureListResponse.getLectures().add(LectureMapper.INSTANCE.lectureToLectureResponse(lecture)));

        return lectureListResponse;
    }

    @Override
    public LectureResponse updateLecture(UpdateLectureRequest request) {

        String userEmail = AuthUtil.getCurrentUserEmail();

        Lecture lecture = lectureRepository.findLectureByIdAndForUser(request.getId(), userEmail)
                .orElseThrow(() -> new DataNotFoundException("Lecture not found!"));

        lecture.setName(request.getName());
        lecture.setUrl(request.getUrl());
        lecture.setDurationBySeconds(request.getDurationBySeconds());

        return LectureMapper.INSTANCE.lectureToLectureResponse(lectureRepository.save(lecture));
    }

    @Override
    public LectureResponse deleteLecture(Long id) {

        String userEmail = AuthUtil.getCurrentUserEmail();

        Lecture lecture = lectureRepository.findLectureByIdAndForUser(id, userEmail)
                .orElseThrow(() -> new DataNotFoundException("Lecture not found!"));

        lectureRepository.delete(lecture);

        return LectureMapper.INSTANCE.lectureToLectureResponse(lecture);
    }

    private Lecture findLectureById(Long id){
        return lectureRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("Lecture not found with "+id+" id!"));
    }
}
