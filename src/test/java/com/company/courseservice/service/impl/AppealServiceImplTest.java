package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.exception.DataNotFoundException;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealListResponse;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.impl.AppealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppealServiceImplTest {

    @Mock
    private AppealRepository appealRepository;

    @InjectMocks
    private AppealServiceImpl appealService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void when_call_create_appeal_given_correct_request_then_return_success() {
        //Arrange
        CreateAppealRequest request = new CreateAppealRequest();
        request.setFullName("TestFullName");
        request.setEmail("test@gmail.com");
        request.setPhoneNumber("+test");
        request.setMessage("testMessage");

        Appeal savedAppeal = new Appeal();
        savedAppeal.setId(1L);
        savedAppeal.setFullName("TestFullName");
        savedAppeal.setEmail("test@gmail.com");
        savedAppeal.setPhoneNumber("+test");
        savedAppeal.setMessage("testMessage");
        when(appealRepository.save(any(Appeal.class))).thenReturn(savedAppeal);

        //Act
        AppealResponse response = appealService.sentAppeal(request);

        //Assert
        assertNotNull(response);
        assertEquals("TestFullName", response.getFullName());
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("+test", response.getPhoneNumber());
        assertEquals("testMessage", response.getMessage());

    }

    @Test
    public void when_call_get_appeal_by_id_given_valid_id_then_return_success() {
        //Arrange
        Appeal existingAppeal = new Appeal();
        existingAppeal.setId(1L);

        when(appealRepository.findById(1L)).thenReturn(Optional.of(existingAppeal));

        //Act
        AppealResponse response = appealService.getAppeal(1L);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void when_call_get_appeal_by_id_given_invalid_id_then_throws_data_not_found_exception(){
        Long id = 1L;
        //Arrange
        Appeal existingAppeal = new Appeal();
        existingAppeal.setId(id);
        when(appealRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(DataNotFoundException.class, () -> appealService.getAppeal(id));
        verify(appealRepository).findById(id);
    }

    @Test
    public void when_call_get_all_appeal_then_return_success() {
        //Arrange
        Page<Appeal> appeals = new PageImpl<>(Arrays.asList(new Appeal(), new Appeal()));
        AppealListResponse expectedResponse = AppealListResponse.builder()
                .items(Arrays.asList(new AppealResponse(), new AppealResponse()))
                .build();
        when(appealRepository.findAll(any(Pageable.class))).thenReturn(appeals);

        //Act
        AppealListResponse response = appealService.getAllAppeals(Pageable.unpaged());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getItems().size(), response.getItems().size());
        verify(appealRepository).findAll(any(Pageable.class));
    }

    @Test
    public void when_call_get_appeal_by_email_then_return_success() {
        //Arrange
        Page<Appeal> appeals = new PageImpl<>(Arrays.asList(new Appeal(), new Appeal()));
        AppealListResponse expectedResponse = AppealListResponse.builder()
                .items(Arrays.asList(new AppealResponse(), new AppealResponse()))
                .build();
        when(appealRepository.findAllByEmailContains(any(String.class), any(Pageable.class))).thenReturn(appeals);

        //Act
        AppealListResponse response = appealService.getAppealsByEmail("test@gmail.com", Pageable.unpaged());

        //Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getItems().size(), response.getItems().size());
        verify(appealRepository).findAllByEmailContains(any(String.class), any(Pageable.class));
    }

}