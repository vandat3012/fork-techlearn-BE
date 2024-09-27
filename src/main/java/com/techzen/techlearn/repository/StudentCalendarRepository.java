package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.TeacherCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface StudentCalendarRepository extends JpaRepository<TeacherCalendar, Integer> {
    @Query("SELECT tc FROM TeacherCalendar tc " +
            "JOIN tc.teacher t " +
            "JOIN tc.user u " +
            "WHERE u.id = :userId and tc.status = BOOKED")
    List<TeacherCalendar> findAllByUserCalendar(@Param("userId") UUID id);

}
