package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.response.StudentCourseResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface StudentCourseService {
    StudentCourseResponseDTO getAllTurnById (UUID idUser);
}
