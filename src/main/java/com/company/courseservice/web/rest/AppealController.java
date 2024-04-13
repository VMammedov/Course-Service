package com.company.courseservice.web.rest;

import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealListResponse;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@Validated
@Slf4j
@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
public class AppealController {
    private final AppealService appealService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppealResponse sentAppeal(@RequestBody @Valid CreateAppealRequest request){
        return appealService.sentAppeal(request);
    }

    @GetMapping
    public AppealListResponse getAllAppeals(Pageable pageable) {
        return appealService.getAllAppeals(pageable);
    }

    @GetMapping("/getAppealsByEmail")
    public AppealListResponse getAppealByEmail(@RequestParam(name = "email") String email, Pageable pageable){
        return appealService.getAppealsByEmail(email, pageable);
    }

    @GetMapping("/{id}")
    public AppealResponse getAppeal(@PathVariable Long id){
        return appealService.getAppeal(id);
    }
}
