package com.techzen.techlearn.mapper;

import com.techzen.techlearn.dto.response.StudentCourseResponseDTO;
import com.techzen.techlearn.entity.StudentCourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentCourseMapper {
    StudentCourseMapper INSTANCE = Mappers.getMapper(StudentCourseMapper.class);

    @Mapping(source = "turn", target = "turn")
    StudentCourseResponseDTO entityToResponseDTO(StudentCourseEntity entity);
}
