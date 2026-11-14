package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Question> getQuestionsOfQuiz(Long quizId) {
        return new HashSet<>(this.questionRepository.findByQuizId(quizId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        if (quiz == null || quiz.getId() == null) {
            return new HashSet<>();
        }
        return new HashSet<>(this.questionRepository.findByQuizId(quiz.getId()));
    }

    @Override
    public void deleteQuestion(Long quesId) {
        this.questionRepository.deleteById(quesId);
    }
}