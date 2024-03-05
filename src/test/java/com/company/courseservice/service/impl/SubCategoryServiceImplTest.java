package com.company.courseservice.service.impl;

import com.company.courseservice.mappers.SubCategoryMapper;
import com.company.courseservice.repository.SubCategoryRepository;
import com.company.courseservice.services.impl.SubCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SubCategoryServiceImplTest {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private SubCategoryMapper subCategoryMapper;

    @InjectMocks
    private SubCategoryServiceImpl subCategoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(SubCategoryServiceImplTest.class);
    }

    @Test
    public void when_call_create_sub_category_given_correct_request_then_return_success(){

    }
}
