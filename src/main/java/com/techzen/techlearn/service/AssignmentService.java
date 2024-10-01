package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.response.PageResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AssignmentService {

    PageResponse<?> getAllAssignments(int page, int pageSize, UUID user);

    Object getAssignmentById(Long id);
}
