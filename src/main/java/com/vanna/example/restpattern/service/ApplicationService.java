package com.vanna.example.restpattern.service;

import com.vanna.example.restpattern.exceptions.ApplicationAPIException;

import java.util.Map;

public interface ApplicationService {
    Map<String, Object> getSuccessObject();

    void throwExceptionOnCall() throws ApplicationAPIException;
}
