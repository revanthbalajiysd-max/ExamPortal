package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;

import java.util.Set;

public interface QuestionService {

    Question addQuestion(Question question);

    Question updateQuestion(Question question);

    Set<Question> getQuestions();

    Question getQuestion(Long questionId);

    Set<Question> getQuestionsOfQuiz(Quiz quiz);

    void deleteQuestion(Long quesId);
}