package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.ReviewConfigRequestDTO;
import com.techzen.techlearn.dto.response.PageResponse;
import com.techzen.techlearn.dto.response.ReviewConfigResponseDTO;
import com.techzen.techlearn.dto.response.UserResponseDTO;
import com.techzen.techlearn.entity.ReviewConfigEntity;
import com.techzen.techlearn.entity.UserEntity;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.ReviewConfigMapper;
import com.techzen.techlearn.repository.ReviewConfigRepository;
import com.techzen.techlearn.service.ReviewConfigService;
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
public class ReviewConfigServiceImpl implements ReviewConfigService {

    ReviewConfigRepository reviewConfigRepository;
    ReviewConfigMapper reviewConfigMapper;



    @Override
    public ReviewConfigResponseDTO getByActive() {

        return reviewConfigMapper.toReviewConfigResponseDTO(reviewConfigRepository.findByActive());
    }


    @Override
    public ReviewConfigResponseDTO saveConfig(ReviewConfigRequestDTO config) {
        ReviewConfigEntity reviewConfig = reviewConfigMapper.toReviewConfigEntity(config);
        reviewConfig.setIsDeleted(false);
        reviewConfig.setIsActive(false);
        return reviewConfigMapper.toReviewConfigResponseDTO(reviewConfigRepository.save(reviewConfig));
    }

    @Override
    public ReviewConfigResponseDTO getById(Long id) {
        return reviewConfigMapper.toReviewConfigResponseDTO(reviewConfigRepository
                .findById(id).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND)));
    }

    @Override
    public ReviewConfigResponseDTO updateConfigActive(Long id) {
        reviewConfigRepository.findAll().forEach(config -> {
            config.setIsActive(false);
            reviewConfigRepository.save(config);
        });
        ReviewConfigEntity reviewConfig = reviewConfigRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        reviewConfig.setIsActive(true);
        reviewConfigRepository.save(reviewConfig);
        return reviewConfigMapper.toReviewConfigResponseDTO(reviewConfigRepository.findByIdActive(id));
    }

    @Override
    public ReviewConfigResponseDTO updateConfig(Long id, ReviewConfigRequestDTO config) {
        var reviewConfigEntity = reviewConfigRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMPT_STRUCTURE_NOT_FOUND));
        var reviewConfig = reviewConfigMapper.toReviewConfigEntity(config);
        reviewConfig.setId(id);
        reviewConfig.setIsDeleted(false);
        reviewConfig.setIsActive(reviewConfigEntity.getIsActive());
        return reviewConfigMapper.toReviewConfigResponseDTO(reviewConfigRepository.save(reviewConfig));
    }

    @Override
    public PageResponse<?> getAllReviewConfig(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, pageSize);
        Page<ReviewConfigEntity> users = reviewConfigRepository.findAll(pageable);
        List<ReviewConfigResponseDTO> list = users.map(reviewConfigMapper::toReviewConfigResponseDTO)
                .stream().collect(Collectors.toList());
        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .items(list)
                .build();
    }
}
