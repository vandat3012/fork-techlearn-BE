package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.StudentCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity,Integer> {
    @Query("SELECT SUM(sc.turn) from StudentCourseEntity sc WHERE sc.user.id =:idUser")
    Integer getAllTurnById (UUID idUser);
}
