package com.company.courseservice.web.rest;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealListResponse;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
public class AppealController {
    private final AppealService appealService;

    @PostMapping("/sentAppeal")
    @ResponseStatus(HttpStatus.CREATED)
    public AppealResponse sentAppeal(@RequestBody CreateAppealRequest request){
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

    @GetMapping("/getAppeal/{id}")
    public AppealResponse getAppeal(@PathVariable Long id){
        return appealService.getAppeal(id);
    }
}
