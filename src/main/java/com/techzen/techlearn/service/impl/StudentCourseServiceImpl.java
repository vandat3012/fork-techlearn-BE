package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.response.StudentCourseResponseDTO;
import com.techzen.techlearn.entity.StudentCourseEntity;
import com.techzen.techlearn.mapper.StudentCourseMapper;
import com.techzen.techlearn.repository.StudentCourseRepository;
import com.techzen.techlearn.service.StudentCourseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentCourseServiceImpl implements StudentCourseService {
    StudentCourseRepository studentCourseRepository;
    StudentCourseMapper studentCourseMapper;

    @Override
    public StudentCourseResponseDTO getAllTurnById(UUID idUser) {
        Integer totalTurn = studentCourseRepository.getAllTurnById(idUser);
        StudentCourseEntity entity = new StudentCourseEntity();
        entity.setTurn(totalTurn);
        StudentCourseResponseDTO dto = studentCourseMapper.entityToResponseDTO(entity);

        return StudentCourseResponseDTO.builder()
                .turn(dto.getTurn())
                .build();
    }
}
