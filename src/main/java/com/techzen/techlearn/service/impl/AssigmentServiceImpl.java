package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.client.AssignmentClient;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.service.AssignmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssigmentServiceImpl implements AssignmentService {

    AssignmentClient assignmentClient;

    @Override
    public PageResponse<?> getAllAssignments(int page, int pageSize, UUID user) {
        var assignments = assignmentClient.getAllAssignment();
        return PageResponse.builder()
                .items(assignments.getBody())
                .build();
    }

    @Override
    public Object getAssignmentById(Long id) {
        var assignment = assignmentClient.getLessonById(id);
        return assignment.getBody();
    }
}
