package com.techzen.techlearn.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointRequestDTO {
    String name;
    String points;
    String price;
    String idCurrency;
}
