package com.techzen.techlearn.service.impl;

import com.techzen.techlearn.dto.request.TeacherCalendarRequestDTO2;
import com.techzen.techlearn.dto.response.TeacherCalendarResponseDTO2;
import com.techzen.techlearn.entity.Mentor;
import com.techzen.techlearn.entity.Teacher;
import com.techzen.techlearn.entity.TeacherCalendar;
import com.techzen.techlearn.entity.UserEntity;
import com.techzen.techlearn.enums.CalendarStatus;
import com.techzen.techlearn.enums.ErrorCode;
import com.techzen.techlearn.exception.AppException;
import com.techzen.techlearn.mapper.TeacherCalendarMapper;
import com.techzen.techlearn.repository.MentorRepository;
import com.techzen.techlearn.repository.TeacherCalendarRepository;
import com.techzen.techlearn.repository.TeacherRepository;
import com.techzen.techlearn.repository.UserRepository;
import com.techzen.techlearn.service.MailService;
import com.techzen.techlearn.service.TeacherCalendar2Service;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherCalendarServiceImpl implements TeacherCalendar2Service {

    TeacherCalendarRepository teacherCalendarRepository;
    TeacherCalendarMapper teacherCalendarMapper;
    TeacherRepository teacherRepository;
    UserRepository userRepository;
    MentorRepository mentorRepository;
    MailService mailService;

    private boolean isTeacher(UUID id) {
        return teacherRepository.existsById(id);
    }

    private boolean isMentor(UUID id) {
        return mentorRepository.existsById(id);
    }

    @Override
    public TeacherCalendarResponseDTO2 createCalendar(TeacherCalendarRequestDTO2 request, UUID id) {

        LocalDateTime timeStart = LocalDateTime.parse(request.getStartTime());
        LocalDateTime timeEnd = LocalDateTime.parse(request.getEndTime());

        LocalDate dateStart = LocalDate.parse(request.getStartTime().substring(0, 10));
        LocalDate dateEnd = LocalDate.parse(request.getEndTime().substring(0, 10));

        if(dateStart.isBefore(LocalDate.now()) || dateEnd.isBefore(LocalDate.now())) {
            throw new AppException(ErrorCode.DATE_APPOINTMENT_NOT_SUITABLE);
        }

        TeacherCalendar entity = teacherCalendarMapper.toEntity(request);

        Teacher teacher;
        Mentor mentor;

        if(isTeacher(id)) {
            teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
            entity.setTeacher(teacher);
        } else if (isMentor(id)) {
            mentor = mentorRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));
            entity.setMentor(mentor);
        } else {
            throw new AppException(ErrorCode.TEACHER_NOT_EXISTED);
        }

        entity.setStartTime(timeStart);
        entity.setEndTime(timeEnd);
        entity.setStatus(CalendarStatus.BUSY);

        TeacherCalendar savedEntity = teacherCalendarRepository.save(entity);

        return teacherCalendarMapper.toDTO(savedEntity);
    }

    @Override
    public List<TeacherCalendarResponseDTO2> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, UUID id) {
        Teacher teacher;
        Mentor mentor;
        UserEntity user;
        List<TeacherCalendar> entities = null;
        List<CalendarStatus> statuses = Arrays.asList(CalendarStatus.BUSY, CalendarStatus.BOOKED);

        if(isTeacher(id)) {
            teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
            entities = teacherCalendarRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndTeacherAndStatusIn(startDate, endDate, teacher, statuses);

        } else if (isMentor(id)) {
            mentor = mentorRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_EXISTED));
            entities = teacherCalendarRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndMentorAndStatusIn(startDate, endDate, mentor, statuses);
        } else {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            entities = teacherCalendarRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndUserAndStatusAndStatusIn(startDate, endDate, user, CalendarStatus.BOOKED, statuses);
        }

        return entities.stream()
                .map(teacherCalendarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherCalendarResponseDTO2> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<CalendarStatus> statuses = Arrays.asList(CalendarStatus.BUSY, CalendarStatus.BOOKED);
        List<TeacherCalendar> calendars = teacherCalendarRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndStatusIn(startDate, endDate, statuses);

        return calendars.stream()
                .map(teacherCalendarMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteTeacherCalendar(Integer id, UUID ownerId) {
        Teacher teacher = teacherRepository.findById(ownerId).orElse(null);
        Mentor mentor = mentorRepository.findById(ownerId).orElse(null);
        TeacherCalendar calendar = null;
        if (teacher != null) {
            calendar = teacherCalendarRepository.findByIdAndTeacher(id, teacher)
                    .orElseThrow(() -> new AppException(ErrorCode.CALENDAR_NOT_EXISTED));

            calendar.setStatus(CalendarStatus.CANCELLED);
            teacherCalendarRepository.save(calendar);
        } else if (mentor != null) {
            calendar = teacherCalendarRepository.findByIdAndMentor(id, mentor)
                    .orElseThrow(() -> new AppException(ErrorCode.CALENDAR_NOT_EXISTED));

            calendar.setStatus(CalendarStatus.CANCELLED);
            teacherCalendarRepository.save(calendar);
        }

        UserEntity user = calendar.getUser();
        user.setPoints(user.getPoints() + 1);
        userRepository.save(user);

        try {
            mailService.sendEmails(
                    new ArrayList<>(Collections.singletonList(user.getEmail())),
                    "Lịch hẹn đã bị hủy",
                    calendar.getTitle(),
                    calendar.getDescription(),
                    calendar.getStartTime(),
                    calendar.getEndTime(),
                    calendar.getDescription(),
                    "Chi tiết",
                    "#e74c3c"  // Màu đỏ
            );
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

    @Override
    @Transactional
    public TeacherCalendarResponseDTO2 updateCalendarTeacher(Integer id, TeacherCalendarRequestDTO2 request) {
        TeacherCalendar entity = teacherCalendarRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CALENDAR_NOT_EXISTED));

        UUID ownerId = UUID.fromString(request.getOwnerId());
        LocalDateTime timeStart = LocalDateTime.parse(request.getStartTime());
        LocalDateTime timeEnd = LocalDateTime.parse(request.getEndTime());

        teacherCalendarMapper.updateEntityFromDTO(request, entity);
        entity.setStartTime(timeStart);
        entity.setEndTime(timeEnd);

        return teacherCalendarMapper.toDTO(entity);
    }

    @Override
    public List<TeacherCalendarResponseDTO2> findCalendarByTeacherId(
            UUID uuid,
            String technicalTeacherName,
            String chapterName
    ) {
        List<TeacherCalendar> calendars = teacherCalendarRepository.findByFilters(
                uuid, technicalTeacherName, chapterName
        );
        if (calendars.isEmpty()) {
            throw new AppException(ErrorCode.CALENDAR_NOT_EXISTED);
        } else return calendars.stream()
                .map(teacherCalendarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherCalendarResponseDTO2> findCourseChapterTeacherMentor(Long idCourse, Long idChapter, LocalDateTime startDate, LocalDateTime endDate) {
//        return teacherCalendarRepository.findCourseChapterTeacherMentor(idCourse, idChapter, startDate, endDate)
//                .stream().map(teacherCalendarMapper::toDTO)
//                .collect(Collectors.toList());

        List<TeacherCalendarResponseDTO2> fakeData = new ArrayList<>();

        // Đối tượng giả đầu tiên
        TeacherCalendarResponseDTO2 dto1 = TeacherCalendarResponseDTO2.builder()
                .id(UUID.randomUUID().toString())
                .title("Lập trình Java")
                .startTime("2024-10-01T10:00:00")
                .endTime("2024-10-01T11:00:00")
                .isAllDay(false)
                .guid(UUID.randomUUID().toString())
                .description("Buổi học lập trình Java cơ bản.")
                .startTimezone("Asia/Ho_Chi_Minh")
                .endTimezone("Asia/Ho_Chi_Minh")
                .ownerId("6929751b-7a4a-11ef-8bc5-005056c00001")
                .recurrenceException(null)
                .recurrenceID(null)
                .recurrenceRule(null)
                .status(CalendarStatus.BUSY)
                .userId("6a1b4eba-fbc6-412b-8219-2a1f84eba567")
                .build();

        // Đối tượng giả thứ hai
        TeacherCalendarResponseDTO2 dto2 = TeacherCalendarResponseDTO2.builder()
                .id(UUID.randomUUID().toString())
                .title("Thiết kế cơ sở dữ liệu")
                .startTime("2024-10-02T14:00:00")
                .endTime("2024-10-02T16:00:00")
                .isAllDay(false)
                .guid(UUID.randomUUID().toString())
                .description("Buổi học về thiết kế cơ sở dữ liệu.")
                .startTimezone("Asia/Ho_Chi_Minh")
                .endTimezone("Asia/Ho_Chi_Minh")
                .ownerId("6929751b-7a4a-11ef-8bc5-005056c00001")
                .recurrenceException(null)
                .recurrenceID(null)
                .recurrenceRule(null)
                .status(CalendarStatus.BOOKED)
                .userId("6a1b4eba-fbc6-412b-8219-2a1f84eba567")
                .build();

        // Thêm vào danh sách
        fakeData.add(dto1);
        fakeData.add(dto2);

        return fakeData;
    }
}
