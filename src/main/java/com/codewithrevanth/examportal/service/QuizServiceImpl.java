package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return this.quizRepository.findById(quizId).orElse(null);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        this.quizRepository.deleteById(quizId);
    }

    @Override
    public Set<Quiz> getQuizzesOfCategory(Category category) {
        return new HashSet<>(this.quizRepository.findByCategory(category));
    }

    @Override
    public Set<Quiz> getActiveQuizzes() {
        return new HashSet<>(this.quizRepository.findByActive(true));
    }

    @Override
    public Set<Quiz> getActiveQuizzesOfCategory(Category category) {
        // Using Java Streams to filter quizzes belonging to a category that are active
        return this.quizRepository.findByCategory(category).stream()
                .filter(Quiz::isActive)
                .collect(Collectors.toSet());
    }
}