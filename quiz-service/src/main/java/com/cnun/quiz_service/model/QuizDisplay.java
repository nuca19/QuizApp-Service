package com.cnun.quiz_service.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Generates a constructor with all fields
public class QuizDisplay {

    private Integer id;
    private String title;
    private List<QuestionWrapper> questions;
}
