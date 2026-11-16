package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.model.QuizResult;
import com.codewithrevanth.examportal.repository.QuizRepository;
import com.codewithrevanth.examportal.repository.QuizResultRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

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
            Hibernate.initialize(quiz.getQuestions());
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

    @Override
    public QuizResult evaluateAndSaveQuiz(Long qId, Map<Long, String> submittedAnswers, String username) {
        Quiz quiz = this.quizRepository.findById(qId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + qId));

        Collection<Question> questions = quiz.getQuestions();
        int totalQuestions = (questions != null) ? questions.size() : 0;

        // Parse maxMarks from Quiz (defaults to total number of questions if null/empty)
        double totalMarks = totalQuestions;
        try {
            if (quiz.getMaxMarks() != null && !quiz.getMaxMarks().isBlank()) {
                totalMarks = Double.parseDouble(quiz.getMaxMarks());
            }
        } catch (NumberFormatException ignored) {}

        double marksPerQuestion = (totalQuestions > 0) ? (totalMarks / totalQuestions) : 0;

        int correctAnswers = 0;
        int attemptedQuestions = 0;

        if (questions != null && submittedAnswers != null) {
            for (Question q : questions) {
                String givenAnswer = submittedAnswers.get(q.getId());
                if (givenAnswer != null && !givenAnswer.isBlank()) {
                    attemptedQuestions++;
                    if (q.getAnswer() != null && givenAnswer.trim().equalsIgnoreCase(q.getAnswer().trim())) {
                        correctAnswers++;
                    }
                }
            }
        }

        int incorrectAnswers = attemptedQuestions - correctAnswers;
        double obtainedMarks = Math.round((correctAnswers * marksPerQuestion) * 100.0) / 100.0;
        double percentage = (totalMarks > 0) ? Math.round(((obtainedMarks / totalMarks) * 100.0) * 100.0) / 100.0 : 0.0;
        boolean passed = percentage >= 40.0;

        QuizResult result = new QuizResult();
        result.setUsername(username != null ? username : "Student");
        result.setQuiz(quiz);
        result.setTotalQuestions(totalQuestions);
        result.setAttemptedQuestions(attemptedQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setIncorrectAnswers(incorrectAnswers);
        result.setTotalMarks(totalMarks);
        result.setObtainedMarks(obtainedMarks);
        result.setPercentage(percentage);
        result.setPassed(passed);

        return quizResultRepository.save(result);
    }
}