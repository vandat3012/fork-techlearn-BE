package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.TeacherRequestDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.TeacherResponseDTO;
import com.techzen.techlearn.entity.Teacher;
import com.techzen.techlearn.mapper.TeacherMapper;
import com.techzen.techlearn.repository.TeacherRepository;
import com.techzen.techlearn.service.TeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherServiceImpl implements TeacherService {

//    TeacherEntityRepository teacherRepository;
    TeacherRepository teacherRepository;
    TeacherMapper teacherMapper;

    @Override
    public PageResponse<?> findAll(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        List<TeacherResponseDTO> list = teachers.map(teacherMapper::toTeacherResponseDTO).stream().collect(Collectors.toList());
        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(teachers.getTotalPages())
                .items(list)
                .build();
    }

    @Override
    public List<TeacherResponseDTO> findAll() {
        return teacherRepository.findAll().stream().map(teacherMapper::toTeacherResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponseDTO addTeacher(TeacherRequestDTO request) {
        Teacher teacher = teacherMapper.toTeacherEntity(request);

        return teacherMapper.toTeacherResponseDTO(teacherRepository.save(teacher));
    }
}
