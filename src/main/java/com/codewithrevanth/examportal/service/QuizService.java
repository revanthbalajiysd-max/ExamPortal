package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Quiz;

import java.util.Set;

public interface QuizService {

    Quiz addQuiz(Quiz quiz);

    Quiz updateQuiz(Quiz quiz);

    Set<Quiz> getQuizzes();

    Quiz getQuiz(Long quizId);

    void deleteQuiz(Long quizId);

    Set<Quiz> getQuizzesOfCategory(Category category);

    Set<Quiz> getActiveQuizzes();

    Set<Quiz> getActiveQuizzesOfCategory(Category category);
}