package com.cnun.quiz_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cnun.quiz_service.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{

    Quiz findByTitle(String title);

}
