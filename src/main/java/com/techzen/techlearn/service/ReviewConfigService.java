package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.request.ReviewConfigRequestDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.ReviewConfigResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReviewConfigService {

    public ReviewConfigResponseDTO getByActive();

    ReviewConfigResponseDTO saveConfig(ReviewConfigRequestDTO config);

    ReviewConfigResponseDTO updateConfig(Long id, ReviewConfigRequestDTO config);

    PageResponse<?> getAllReviewConfig(int page, int pageSize);

    ReviewConfigResponseDTO getById(Long id);

    ReviewConfigResponseDTO updateConfigActive(Long id);
}
