package com.cnun.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cnun.question_service.dao.QuestionDao;
import com.cnun.question_service.model.Question;
import com.cnun.question_service.model.QuestionResponse;
import com.cnun.question_service.model.QuestionWrapper;


@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }

    public String removeQuestion(int id) {
        questionDao.deleteById(id);
        return "success"; 
    }

    public ResponseEntity<List<Integer>> getQuestionsforQuiz(String category, int num) {
        List<Integer> questionsIDs = questionDao.findbyCatandNum(category, num);
        return new ResponseEntity<>(questionsIDs, HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionsIDs) {
        List<QuestionWrapper> result = new ArrayList<>();

        List<Question> fullquestions = new ArrayList<>();
        for (Integer id : questionsIDs){
            fullquestions.add(questionDao.findById(id).get());
        }

        for (Question q : fullquestions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getCategory(), q.getQuestion(), q.getOption1(), q.getOption2());
            result.add(qw);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuestionResponse> responses) {
        int score = 0;
        for (QuestionResponse qr : responses){
            Question q = questionDao.findById(qr.getId()).get();
            if (q.getAnswer().equals(qr.getAnswer())){
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
