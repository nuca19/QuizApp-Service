package com.cnun.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnun.quiz_service.model.QuestionResponse;
import com.cnun.quiz_service.model.QuestionWrapper;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String category, @RequestParam int num);

    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionsIDs);

    @PostMapping("questions/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionResponse> responses);
}
