package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.service.QuestionService;
import com.codewithrevanth.examportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizForTest(@PathVariable("qid") Long qid) {
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questionSet = this.questionService.getQuestionsOfQuiz(quiz);
        List<Question> list = new ArrayList<>(questionSet);

        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()));
        }

        Collections.shuffle(list);

        for (Question q : list) {
            q.setAnswer("");
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{quesId}")
    public Question getQuestion(@PathVariable("quesId") Long quesId) {
        return this.questionService.getQuestion(quesId);
    }

    @DeleteMapping("/{quesId}")
    public void deleteQuestion(@PathVariable("quesId") Long quesId) {
        this.questionService.deleteQuestion(quesId);
    }

    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;

        for (Question q : questions) {
            if (q.getGivenAnswer() != null && !q.getGivenAnswer().trim().isEmpty()) {
                attempted++;
            }

            Question originalQuestion = this.questionService.getQuestion(q.getId());

            if (originalQuestion != null && originalQuestion.getAnswer() != null
                    && originalQuestion.getAnswer().trim().equals(q.getGivenAnswer())) {
                correctAnswers++;
            }
        }

        if (!questions.isEmpty() && questions.get(0).getQuiz() != null && questions.get(0).getQuiz().getId() != null) {
            Quiz quiz = this.quizService.getQuiz(questions.get(0).getQuiz().getId());
            if (quiz != null && quiz.getMaxMarks() != null) {
                double totalMaxMarks = Double.parseDouble(quiz.getMaxMarks());
                double marksSingle = totalMaxMarks / questions.size();
                marksGot = correctAnswers * marksSingle;
            }
        }

        Map<String, Object> response = Map.of(
                "marksGot", marksGot,
                "correctAnswers", correctAnswers,
                "attempted", attempted
        );

        return ResponseEntity.ok(response);
    }
}