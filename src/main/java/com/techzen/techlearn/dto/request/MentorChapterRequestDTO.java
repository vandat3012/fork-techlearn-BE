package com.techzen.techlearn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorChapterRequestDTO {
    private String mentorId;
    private String chapterId;
}
