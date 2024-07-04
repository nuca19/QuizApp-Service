package com.cnun.quiz_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cnun.quiz_service.dao.QuizDao;
import com.cnun.quiz_service.feign.QuizInterface;
import com.cnun.quiz_service.model.Question;
import com.cnun.quiz_service.model.QuestionResponse;
import com.cnun.quiz_service.model.QuestionWrapper;
import com.cnun.quiz_service.model.Quiz;
import com.cnun.quiz_service.model.QuizDisplay;



@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int num, String title) {
        // RestTemplate restTemplate = new RestTemplate();
        // restTemplate.(http://localhost:8080/question/generate); //call question-service generate

        List<Integer> qids = quizInterface.getQuestionsforQuiz(category, num).getBody();
        //get questions from ids calling question service

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIDs(qids);

        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);

        List<Integer> qids = quiz.get().getQuestionIDs();

        List<QuestionWrapper> questions = quizInterface.getQuestions(qids).getBody();

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<String> resolveQuiz(int id, List<QuestionResponse> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        int numq = quiz.get().getQuestionIDs().size();
        int score = (int)(((double)quizInterface.getScore(responses).getBody() / numq) * 100);
        String res = "grade: " + score +"%";
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<List<QuizDisplay>> getAllQuizes() {
        
        List<Quiz> quizes = quizDao.findAll();

        List<QuizDisplay> res= new ArrayList<>(); 

        for (Quiz quiz : quizes){
            List<Integer> qids = quiz.getQuestionIDs();

            List<QuestionWrapper> questions = quizInterface.getQuestions(qids).getBody();
            QuizDisplay qd = new QuizDisplay(quiz.getId(), quiz.getTitle(), questions);
            res.add(qd);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
