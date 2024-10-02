package com.techzen.techlearn.dto.response;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructResponseAIDTO {

    Long id;
    String contentStruct;
    Boolean isActive;
    String type;

}
