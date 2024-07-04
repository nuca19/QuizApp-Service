package com.cnun.question_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cnun.question_service.model.Question;
import com.cnun.question_service.model.QuestionResponse;
import com.cnun.question_service.model.QuestionWrapper;
import com.cnun.question_service.service.QuestionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("all")    
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Define a method for each category with the full path
    @GetMapping("category/{category}")    
    public ResponseEntity<List<Question>> getQuestionsByCategoryMath(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public String removeQuestion(@PathVariable int id){
        questionService.removeQuestion(id);
        return "success removing";
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String category, @RequestParam int num) {
        return questionService.getQuestionsforQuiz(category, num);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionsIDs) {
        return questionService.getQuestions(questionsIDs);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionResponse> responses){
        return questionService.getScore(responses);
    }
    
    
}
    


