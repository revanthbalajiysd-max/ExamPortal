package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Spring Transactional

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional // Ensures Hibernate session remains active for database operations here
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
        Quiz quiz = this.quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            // Force initialization of questions collection while transaction session is open
            quiz.getQuestions().size();
        }
        return quiz;
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
        return this.quizRepository.findByCategory(category).stream()
                .filter(Quiz::isActive)
                .collect(Collectors.toSet());
    }
}