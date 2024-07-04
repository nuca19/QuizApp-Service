package com.cnun.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnun.quiz_service.QuizServiceApplication;
import com.cnun.quiz_service.model.QuestionResponse;
import com.cnun.quiz_service.model.QuestionWrapper;
import com.cnun.quiz_service.model.Quiz;
import com.cnun.quiz_service.model.QuizDisplay;
import com.cnun.quiz_service.model.QuizDto;
import com.cnun.quiz_service.service.QuizService;



@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNum(), quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @GetMapping("getall")
    public ResponseEntity<List<QuizDisplay>> getAllQuizes() {
        return quizService.getAllQuizes();
    }
    

    @PostMapping("resolve/{id}")
    public ResponseEntity<String> resolveQuiz(@PathVariable int id, @RequestBody List<QuestionResponse> responses) {
        return quizService.resolveQuiz(id, responses);
    }
    
}
