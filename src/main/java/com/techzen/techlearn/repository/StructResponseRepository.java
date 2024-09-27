package com.techzen.techlearn.repository;

import com.techzen.techlearn.entity.StructResponseAI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructResponseRepository extends JpaRepository<StructResponseAI,Long> {

    public List<StructResponseAI> findAllByTypeLike(String type);

}
