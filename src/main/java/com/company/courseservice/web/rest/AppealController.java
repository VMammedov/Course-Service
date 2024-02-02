package com.company.courseservice.web.rest;

import com.company.courseservice.domain.Appeal;
import com.company.courseservice.request.SentMessageRequest;
import com.company.courseservice.services.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appeal")
@RequiredArgsConstructor
public class AppealController {
    private final AppealService appealService;

    @PostMapping
    public ResponseEntity<Appeal> sentAppeal(@RequestBody SentMessageRequest request){
        return ResponseEntity.ok(appealService.sentAppeal(request));
    }

    @GetMapping
    public ResponseEntity<List<Appeal>> getAllAppeals(){
        return ResponseEntity.ok(appealService.getAllAppeals());
    }

    @GetMapping("{email}")
    public ResponseEntity<Appeal> getAppealByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(appealService.getAppealByEmail(email));
    }

}
