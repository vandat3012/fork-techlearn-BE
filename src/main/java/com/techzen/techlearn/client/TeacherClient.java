package com.techzen.techlearn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teacherService", url = "${application.urlClient}/courses")
public interface TeacherClient {

    @GetMapping("/{id}/teacher")
    ResponseEntity<?> getTeacherByIdCourse(@PathVariable Long id);

}