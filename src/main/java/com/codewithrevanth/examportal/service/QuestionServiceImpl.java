package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).orElse(null);
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return new HashSet<>(this.questionRepository.findByQuiz(quiz));
    }

    @Override
    public void deleteQuestion(Long quesId) {
        this.questionRepository.deleteById(quesId);
    }
}