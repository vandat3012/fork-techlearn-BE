package com.techzen.techlearn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorResponseDTO {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("OwnerText")
    private String name;
    private String avatar;
    @JsonProperty("OwnerColor")
    private String color;
}