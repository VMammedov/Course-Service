package com.company.courseservice.service.impl;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.repository.AppealRepository;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.impl.AppealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void when_given_id_then_return_success() {
        //Arrange
        Appeal existingAppeal = new Appeal();
        existingAppeal.setId(1L);
        existingAppeal.setFullName("TestFullName");
        existingAppeal.setEmail("test@gmail.com");
        existingAppeal.setPhoneNumber("+test");
        existingAppeal.setMessage("testMessage");
        when(appealRepository.findById(1L)).thenReturn(Optional.of(existingAppeal));
        //Act
        AppealResponse response = appealService.getAppeal(1L);

        //Assert
        assertNotNull(response);
    }


    @Test
    public void when_call_get_all_appeal_then_return_success() {
        //Arrange
        List<Appeal> appeals = Arrays.asList(new Appeal(), new Appeal());
        when(appealRepository.findAll()).thenReturn(appeals);
        //Act
        List<AppealResponse> response = appealService.getAllAppeals();

        //Assert
        assertNotNull(response);
    }

    @Test
    public void when_call_get_appeal_by_email_then_return_success() {
        //Arrange
        Appeal appeal1 = new Appeal();
        appeal1.setId(1L);
        appeal1.setFullName("TestFullName");
        appeal1.setEmail("test@gmail.com");
        appeal1.setPhoneNumber("+test");
        appeal1.setMessage("testMessage");

        Appeal appeal2 = new Appeal();
        appeal2.setId(2L);
        appeal2.setFullName("TestFullName");
        appeal2.setEmail("test@gmail.com");
        appeal2.setPhoneNumber("+test");
        appeal2.setMessage("testMessage");
        List<Appeal> appeals = Arrays.asList(appeal1, appeal2);

        when(appealRepository.findAllByEmail("test@gmail.com")).thenReturn(appeals);
        //Act
        List<AppealResponse> responses = appealService.getAppealsByEmail("test@gmail.com");
        //Assert
        assertNotNull(responses);

    }

}