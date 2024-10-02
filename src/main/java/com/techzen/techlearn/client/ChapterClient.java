package com.techzen.techlearn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "chapterService", url = "${application.urlClient}/courses")
public interface ChapterClient {

    @GetMapping("/{id}/chapters")
    ResponseEntity<?> getChapterByIdCourse(@PathVariable Long id);

}