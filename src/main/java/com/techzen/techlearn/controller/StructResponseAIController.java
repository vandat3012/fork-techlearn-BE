package com.techzen.techlearn.controller;

import com.techzen.techlearn.dto.response.ResponseData;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.service.impl.StructResponseServiceImpl;
import com.techzen.techlearn.service.impl.TypeStructResponseServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/struct-responses")
public class StructResponseAIController {

    StructResponseServiceImpl structResponseService;
    TypeStructResponseServiceImpl typeStructResponseService;

    @GetMapping
    public ResponseData<?> getStructResponse() {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(structResponseService.getAll())
                .build();
    }
    @GetMapping("type-struct")
    public ResponseData<?> getAllTypeStruct() {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(typeStructResponseService.getListTypeStruct())
                .build();
    }
    @GetMapping("{idStruct}")
    public ResponseData<?> getStructResponseById(@PathVariable("idStruct") Long idStruct) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(structResponseService.getById(idStruct))
                .build();
    }

    @PostMapping
    public ResponseData<?> getStructResponse(@RequestParam String contentStruct,@RequestParam String struct) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(structResponseService.saveStructResponse(contentStruct,struct))
                .build();
    }

    @PatchMapping("{idStruct}")
    public  ResponseData<?> activeStruct(@PathVariable("idStruct") Long idStruct) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(structResponseService.activeStructResponse(idStruct))
                .build();

    }
    @PutMapping("{idStruct}")
    public  ResponseData<?> updateStruct(@PathVariable("idStruct") Long idStruct,@RequestParam String contentStruct,@RequestParam String struct) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(structResponseService.updateStructResponse(contentStruct,struct,idStruct))
                .build();

    }
    @DeleteMapping("{idStruct}")
    public  ResponseData<?> deleteStruct(@PathVariable("idStruct") Long idStruct) {
        structResponseService.deleteStruct(idStruct);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .build();

    }

}
