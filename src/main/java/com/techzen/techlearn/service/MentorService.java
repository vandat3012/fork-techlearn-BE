package com.techzen.techlearn.service;

import com.techzen.techlearn.dto.request.MentorChapterRequestDTO;
import com.techzen.techlearn.dto.request.MentorRequestDTO;
import com.techzen.techlearn.dto.response.MentorResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MentorService {
    MentorResponseDTO createMentor(MentorRequestDTO dto);

    List<MentorResponseDTO> findAllMentor();

    List<MentorResponseDTO> filterMentorByChapter(Long id);

    MentorResponseDTO addMentorToChapter(UUID uuid, Long id);

    MentorResponseDTO updateMentorToChapter(UUID uuid, Long id, MentorChapterRequestDTO request);

    void deleteMentorToChapter(UUID uuid, Long id);
}
