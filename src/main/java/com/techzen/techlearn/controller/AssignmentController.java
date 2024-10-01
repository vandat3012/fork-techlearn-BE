package com.techzen.techlearn.controller;

import com.techzen.techlearn.service.AssignmentService;
import com.techzen.techlearn.util.JsonResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<?> getAllAssignment(@RequestParam(defaultValue = "1", required = false) int page,
                                              @RequestParam(defaultValue = "10", required = false) int pageSize,
                                              @RequestParam(required = false) UUID userId) {
        return JsonResponse.ok(assignmentService.getAllAssignments(page, pageSize, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignmentById(@PathVariable long id) {
        return JsonResponse.ok(assignmentService.getAssignmentById(id));
    }
}
