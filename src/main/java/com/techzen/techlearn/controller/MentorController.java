package com.techzen.techlearn.controller;

import com.techzen.techlearn.dto.request.MentorChapterRequestDTO;
import com.techzen.techlearn.dto.request.MentorRequestDTO;
import com.techzen.techlearn.dto.request.TeacherRequestDTO;
import com.techzen.techlearn.dto.response.MentorResponseDTO;
import com.techzen.techlearn.dto.response.ResponseData;
import com.techzen.techlearn.dto.response.TeacherResponseDTO;
import com.techzen.techlearn.entity.Mentor;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.service.MentorService;
import com.techzen.techlearn.service.TeacherService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/mentors")
public class MentorController {

    MentorService mentorService;

//    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseData<?> findAll(@RequestParam(required = false, defaultValue = "1") int page,
//                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
//        return ResponseData.builder()
//                .status(HttpStatus.OK.value())
//                .code(ErrorCode.GET_SUCCESSFUL.getCode())
//                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
//                .result(teacherService.findAll(page, pageSize))
//                .build();
//    }

    @GetMapping("/")
    public List<MentorResponseDTO> findAll() {
        return mentorService.findAllMentor();
    }

    @PostMapping
    public ResponseData<?> add(@RequestBody @Valid MentorRequestDTO request) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.ADD_SUCCESSFUL.getCode())
                .message(ErrorCode.ADD_SUCCESSFUL.getMessage())
                .result(mentorService.createMentor(request))
                .build();
    }

    @GetMapping("/chapter/{id}")
    public ResponseData<?> filterMentorByChapter(@PathVariable Long id) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .result(mentorService.filterMentorByChapter(id))
                .build();
    }

    @PostMapping("{mentor_id}/create-mentor-chapter/{chapter_id}")
    public ResponseData<?> addMentorToChapter(@PathVariable UUID mentor_id, @PathVariable Long chapter_id) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.ADD_SUCCESSFUL.getCode())
                .message(ErrorCode.ADD_SUCCESSFUL.getMessage())
                .result(mentorService.addMentorToChapter(mentor_id, chapter_id))
                .build();
    }

    @PutMapping("/{mentor_id}/update-mentor-chapter/{chapter_id}")
    public ResponseData<?> updateMentorToChapter(@PathVariable UUID mentor_id, @PathVariable Long chapter_id, @RequestBody @Valid MentorChapterRequestDTO request) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.UPDATE_SUCCESSFUL.getCode())
                .message(ErrorCode.UPDATE_SUCCESSFUL.getMessage())
                .result(mentorService.updateMentorToChapter(mentor_id, chapter_id, request))
                .build();
    }

    @DeleteMapping("/{uuid}/delete-mentor-chapter/{id}")
    public ResponseData<?> deleteMentorToChapter(@PathVariable UUID uuid, @PathVariable Long id) {
        mentorService.deleteMentorToChapter(uuid, id);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.DELETE_SUCCESSFUL.getCode())
                .message(ErrorCode.DELETE_SUCCESSFUL.getMessage())
                .result("Deleted mentor chapter by chapter_id " + id + "mentor_id " + uuid)
                .build();
    }
}

