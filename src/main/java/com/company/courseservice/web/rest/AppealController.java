package com.company.courseservice.web.rest;

import com.company.courseservice.request.Appeal.CreateAppealRequest;
import com.company.courseservice.response.Appeal.AppealResponse;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getAllAppeals")
    public List<AppealResponse> getAllAppeals(){
        return appealService.getAllAppeals();
    }

    @GetMapping("/getAppealsByEmail")
    public List<AppealResponse> getAppealByEmail(@RequestParam(name = "email") String email){
        return appealService.getAppealsByEmail(email);
    }

}
