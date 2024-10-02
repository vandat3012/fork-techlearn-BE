package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.PointRequestDTO;
import com.techzen.techlearn.dto.response.MentorResponseDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.PointResponseDTO;
import com.techzen.techlearn.dto.response.TeacherResponseDTO;
import com.techzen.techlearn.entity.PointEntity;
import com.techzen.techlearn.entity.Teacher;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.PointMapper;
import com.techzen.techlearn.repository.PointRepository;
import com.techzen.techlearn.service.PointService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PointServiceImpl implements PointService {
    final PointRepository pointRepository;
    final PointMapper pointMapper;
    @Override
    public PointResponseDTO createPoints(PointRequestDTO requestDTO) {
        PointEntity point = pointMapper.toPointEntity(requestDTO);
        return pointMapper.toPointResponseDTO(pointRepository.save(point));
    }

    @Override
    public PageResponse<?> findAllPoints(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<PointEntity> points = pointRepository.findAll(pageable);
        List<PointResponseDTO> list = points.map(pointMapper::toPointResponseDTO).stream().collect(Collectors.toList());
        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(points.getTotalPages())
                .items(list)
                .build();
    }

    @Override
    public PointResponseDTO findPointsById(Integer id) {
        PointEntity point = pointRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POINT_NOT_FOUND));
        return pointMapper.toPointResponseDTO(point);
    }

    @Override
    public PointResponseDTO updatePoint(Integer id,PointRequestDTO pointRequestDTO) {
        PointEntity point = pointRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POINT_NOT_FOUND));
        var newPoint = pointMapper.toPointEntity(pointRequestDTO);
        point.setName(newPoint.getName());
        point.setPrice(newPoint.getPrice());
        point.setPoints(newPoint.getPoints());
        point.setCurrency(newPoint.getCurrency());
        return pointMapper.toPointResponseDTO(pointRepository.save(point));
    }

    @Override
    public void deletePointsById(Integer id) {
        pointRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POINT_NOT_FOUND));
        pointRepository.deleteById(id);
    }
}
