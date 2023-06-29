package com.vanna.example.restpattern.service;

import com.vanna.example.restpattern.exceptions.ApplicationAPIException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ApplicationService {

    public Map<String, Object> getSuccessObject() {
        Map<String, Object> applicationInfo = new HashMap<>();
        applicationInfo.put("environment", "DEV-01");
        applicationInfo.put("buildIdentifier", UUID.randomUUID());
        applicationInfo.put("version", "1.98");
        applicationInfo.put("buildTimestamp", LocalDateTime.now());
        return applicationInfo;
    }

    public void throwExceptionOnCall() throws ApplicationAPIException {
        throw new ApplicationAPIException(
                "Error fetching the student with ID "+UUID.randomUUID(),
                400,
                "ERR-STUDENT-NOT-FOUND",
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now()
        );
    }
}
