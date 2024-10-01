package com.techzen.techlearn.controller;

import com.techzen.techlearn.service.ChapterService;
import com.techzen.techlearn.util.JsonResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/chapters")
public class ChapterController {

    ChapterService chapterService;

    @GetMapping
    public ResponseEntity<?> getChapterByIdCourse(@RequestParam Long idCourse) {
        return JsonResponse.ok(chapterService.getAllChaptersByCourseId(idCourse));
    }

}
