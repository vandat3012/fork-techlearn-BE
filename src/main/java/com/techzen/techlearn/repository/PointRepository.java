package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity,Integer> {
}
