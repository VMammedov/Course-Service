package com.company.courseservice.services.impl;

import com.company.courseservice.domain.Lecture;
import com.company.courseservice.exception.IllegalRequestException;
import com.company.courseservice.mappers.LectureMapper;
import com.company.courseservice.repository.CourseRepository;
import com.company.courseservice.repository.LectureRepository;
import com.company.courseservice.request.Lecture.CreateBulkLectureRequest;
import com.company.courseservice.request.Lecture.CreateLectureRequest;
import com.company.courseservice.request.Lecture.UpdateBulkLectureRequest;
import com.company.courseservice.request.Lecture.UpdateLectureRequest;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public CreateLectureResponse createBulkLecture(CreateBulkLectureRequest request) {

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

        for (CreateLectureRequest item : request.getLectures()) {
            Long sectionId = item.getSectionId();

            if (!sectionIds.contains(sectionId)) {
                sectionIds.add(sectionId);

                if (!courseRepository.existsSectionForCourse(courseId, sectionId)) {
                    throw new IllegalRequestException("Section does not belong to the course!");
                }
            }
            item.setCourseId(courseId);
            lectures.add(LectureMapper.INSTANCE.createLectureRequestToLecture(item));
        }
        lectureRepository.saveAll(lectures);
        return null;
    }

    @Override
    public LectureResponse getLectureById(Long id) {
        return null;
    }

    @Override
    public LectureListResponse getLectureList(Long courseId, Long sectionId) {
        return null;
    }

    @Override
    public LectureResponse updateLecture(UpdateLectureRequest request) {
        return null;
    }

    @Override
    public LectureResponse updateBulkLecture(UpdateBulkLectureRequest request) {
        return null;
    }

    @Override
    public LectureResponse deleteLecture(Long id) {
        return null;
    }
}
