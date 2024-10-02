package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.client.ChapterClient;
import com.techzen.techlearn.client.CourseClient;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.service.ChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterServiceImpl implements ChapterService {

    CourseClient courseClient;

    @Override
    public PageResponse<?> getAllChaptersByCourseId(Long courseId) {
        var chapters = courseClient.getChapterByIdCourse(courseId);
        return PageResponse.builder()
                .items(chapters.getBody())
                .build();
    }
}
