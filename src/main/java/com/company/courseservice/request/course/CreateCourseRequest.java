package com.company.courseservice.request.course;

import com.company.courseservice.domain.SubCategory;
import com.company.courseservice.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {

    private String name;
    private String description;
    private Double price;
    private boolean haveCertificate;

//    private SubCategory subCategory;
}
