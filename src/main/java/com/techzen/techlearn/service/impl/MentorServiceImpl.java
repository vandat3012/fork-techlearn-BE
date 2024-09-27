package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.MentorChapterRequestDTO;
import com.techzen.techlearn.dto.request.MentorRequestDTO;
import com.techzen.techlearn.dto.response.MentorResponseDTO;
import com.techzen.techlearn.entity.ChapterEntity;
import com.techzen.techlearn.entity.Mentor;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.MentorMapper;
import com.techzen.techlearn.repository.ChapterRepository;
import com.techzen.techlearn.repository.MentorRepository;
import com.techzen.techlearn.service.MentorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MentorServiceImpl implements MentorService {

    MentorRepository mentorRepository;
    MentorMapper mentorMapper;
    ChapterRepository chapterRepository;

    @Override
    public MentorResponseDTO createMentor(MentorRequestDTO dto) {
        Mentor mentor = mentorMapper.toEntity(dto);

        if(mentor.getId() == null) {
            mentor.setId(UUID.randomUUID());
        }

        return mentorMapper.toTeacherResponseDTO(mentorRepository.save(mentor));
    }

    @Override
    public List<MentorResponseDTO> findAllMentor() {
        return mentorRepository.findAll().stream().map(mentorMapper::toTeacherResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MentorResponseDTO> filterMentorByChapter(Long id) {
        List<Mentor> mentors = mentorRepository.findMentorByChapter(id);
        if (mentors.isEmpty()) {
            throw new AppException(ErrorCode.MENTOR_NOT_EXISTED);
        } else return mentors.stream()
                .map(mentorMapper::toTeacherResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MentorResponseDTO addMentorToChapter(UUID uuid, Long id) {
        Mentor mentor = mentorRepository.findById(uuid)
                .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));

        ChapterEntity chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_EXISTED));
        mentor.getChapters().add(chapter);
        Mentor updatedMentor = mentorRepository.save(mentor);
        return mentorMapper.toTeacherResponseDTO(updatedMentor);
    }

    @Override
    public MentorResponseDTO updateMentorToChapter(UUID uuid, Long oldChapterId, MentorChapterRequestDTO request) {
        Mentor mentor = mentorRepository.findById(uuid)
                .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));
        Long newChapterId = Long.valueOf(request.getChapterId());
        mentorRepository.updateMentorChapter(oldChapterId, newChapterId);
        Mentor updatedMentor = mentorRepository.findById(uuid)
                .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));
        return mentorMapper.toTeacherResponseDTO(updatedMentor);
    }


    @Override
    public void deleteMentorToChapter(UUID uuid, Long id) {
        mentorRepository.findById(uuid)
                .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));
        chapterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CHAPTER_NOT_EXISTED));
        mentorRepository.deleteMentorChapter(uuid, id);
    }

}
