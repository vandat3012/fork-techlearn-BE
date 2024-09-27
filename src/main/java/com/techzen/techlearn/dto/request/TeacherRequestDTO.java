package com.techzen.techlearn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDTO {
    private String id;
    private String name;
    private String avatar;
    private String color;
    private String email;
}
