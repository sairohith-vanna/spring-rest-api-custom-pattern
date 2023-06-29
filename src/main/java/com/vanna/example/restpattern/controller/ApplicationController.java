package com.vanna.example.restpattern.controller;

import com.vanna.example.restpattern.exceptions.ApplicationAPIException;
import com.vanna.example.restpattern.service.ApplicationService;
import com.vanna.example.restpattern.service.ApplicationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/info")
public class ApplicationController extends RestControllerBase {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationServiceImpl applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<?> getAppInfo() {
        return ok(applicationService.getSuccessObject());
    }

    @GetMapping("tracked")
    public ResponseEntity<?> getAppInfoWithCustomTracking() {
        return ok(applicationService.getSuccessObject(), UUID.randomUUID(), UUID.randomUUID());
    }

    @GetMapping("custom-status")
    public ResponseEntity<?> getAppInfoWithCustomStatus() {
        return success(applicationService.getSuccessObject(), HttpStatus.FOUND);
    }

    @GetMapping("error")
    public ResponseEntity<?> getTraceableError() {
        try {
            applicationService.throwExceptionOnCall();
            return null;
        } catch (ApplicationAPIException e) {
            return error(e);
        }
    }
}
