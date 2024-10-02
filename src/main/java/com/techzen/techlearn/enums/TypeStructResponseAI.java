package com.techzen.techlearn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeStructResponseAI {
    language("language"),struct("struct");
    ;
    String name;
}
