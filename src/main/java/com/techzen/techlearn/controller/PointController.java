package com.techzen.techlearn.controller;

import com.techzen.techlearn.dto.request.PointRequestDTO;
import com.techzen.techlearn.dto.response.PointResponseDTO;
import com.techzen.techlearn.dto.response.ResponseData;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.service.impl.PointServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/points")
public class PointController {
    PointServiceImpl pointService;

    @GetMapping
    public List<PointResponseDTO> findAll() {
        return pointService.findAllPoints();
    }
    @GetMapping("/{id}")
    public ResponseData<?> getPointsById(@PathVariable Integer id) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(pointService.findPointsById(id))
                .build();
    }

    @PostMapping
    public ResponseData<?> create(@RequestBody @Valid PointRequestDTO request) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.ADD_SUCCESSFUL.getCode())
                .message(ErrorCode.ADD_SUCCESSFUL.getMessage())
                .result(pointService.createPoints(request))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateUser(@PathVariable Integer id,@RequestBody PointRequestDTO pointRequestDTO) {
                return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.UPDATE_SUCCESSFUL.getCode())
                .message(ErrorCode.UPDATE_SUCCESSFUL.getMessage())
                .result(pointService.updatePoint(id,pointRequestDTO))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deletePoints(@PathVariable Integer id) {
        pointService.deletePointsById(id);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.DELETE_SUCCESSFUL.getCode())
                .message(ErrorCode.DELETE_SUCCESSFUL.getMessage())
                .result("Deleted point by id " + id)
                .build();
    }
}
