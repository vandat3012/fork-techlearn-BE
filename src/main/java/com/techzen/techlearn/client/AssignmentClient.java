package com.techzen.techlearn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assignmentService", url = "${application.urlClient}/lessons")
public interface AssignmentClient {

    @GetMapping("/assignments")
    ResponseEntity<?> getAllAssignment();

    @GetMapping("/{id}")
    ResponseEntity<?> getLessonById(@PathVariable Long id);

}