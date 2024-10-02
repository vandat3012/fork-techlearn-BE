package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.request.PointRequestDTO;
import com.techzen.techlearn.dto.request.UserRequestDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.PointResponseDTO;
import com.techzen.techlearn.dto.response.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PointService {
    PointResponseDTO createPoints(PointRequestDTO requestDTO);
    PageResponse<?> findAllPoints(int page, int pageSize);
    PointResponseDTO findPointsById(Integer id);
    PointResponseDTO updatePoint(Integer id,PointRequestDTO pointRequestDTO);
    void deletePointsById (Integer id);

}
