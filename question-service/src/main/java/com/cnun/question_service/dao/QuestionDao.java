package com.cnun.question_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cnun.question_service.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
    @Query("SELECT q FROM Question q WHERE q.category = ?1") //jpql
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM Question q WHERE q.category=:category LIMIT :num", nativeQuery = true) //postgresql
    List<Integer> findbyCatandNum(String category, int num);
}
