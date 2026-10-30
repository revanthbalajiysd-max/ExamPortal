package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping("/")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return this.quizService.addQuiz(quiz);
    }

    @PutMapping("/")
    public Quiz updateQuiz(@RequestBody Quiz quiz) {
        return this.quizService.updateQuiz(quiz);
    }

    @GetMapping("/")
    public Set<Quiz> getQuizzes() {
        return this.quizService.getQuizzes();
    }

    @GetMapping("/{quizId}")
    public Quiz getQuiz(@PathVariable("quizId") Long quizId) {
        return this.quizService.getQuiz(quizId);
    }
    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable("quizId") Long quizId) {
        this.quizService.deleteQuiz(quizId);
    }

    @GetMapping("/category/{cid}")
    public Set<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesOfCategory(category);
    }

    @GetMapping("/active")
    public Set<Quiz> getActiveQuizzes() {
        return this.quizService.getActiveQuizzes();
    }

    @GetMapping("/category/active/{cid}")
    public Set<Quiz> getActiveQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }
}