package com.techzen.techlearn.mapper;

import com.techzen.techlearn.dto.request.TeacherRequestDTO;
import com.techzen.techlearn.dto.response.TeacherResponseDTO;
import com.techzen.techlearn.entity.Teacher;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.repository.TeacherRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "email", source = "email")
    Teacher toTeacherEntity(TeacherRequestDTO dto);

    TeacherResponseDTO toTeacherResponseDTO(Teacher entity);
}
