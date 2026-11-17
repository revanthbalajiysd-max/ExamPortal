package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.dto.QuizAnalyticsDto;
import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.model.QuizResult;

import java.util.List;
import java.util.Map;
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

    QuizResult evaluateAndSaveQuiz(Long qId, Map<Long, String> submittedAnswers, String username);

    QuizAnalyticsDto getQuizAnalytics(Long qId);

    List<QuizResult> getStudentHistory(String username);
}