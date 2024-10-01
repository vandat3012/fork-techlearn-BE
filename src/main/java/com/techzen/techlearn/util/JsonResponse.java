package com.techzen.techlearn.util;

import com.techzen.techlearn.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {
    public static <T> ResponseEntity<ResponseData<T>> ok(T data) {
        return ResponseEntity.ok(ResponseData.<T>builder().result(data).build());
    }

    public static <T> ResponseEntity<ResponseData<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.<T>builder().result(data).build());
    }

    public static <T> ResponseEntity<ResponseData<T>> deleted() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
