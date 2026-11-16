package com.codewithrevanth.examportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int totalQuestions;
    private int attemptedQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private double totalMarks;
    private double obtainedMarks;
    private double percentage;
    private boolean passed;
    private LocalDateTime submittedAt;

    public QuizResult() {
        this.submittedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getAttemptedQuestions() { return attemptedQuestions; }
    public void setAttemptedQuestions(int attemptedQuestions) { this.attemptedQuestions = attemptedQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getIncorrectAnswers() { return incorrectAnswers; }
    public void setIncorrectAnswers(int incorrectAnswers) { this.incorrectAnswers = incorrectAnswers; }

    public double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }

    public double getObtainedMarks() { return obtainedMarks; }
    public void setObtainedMarks(double obtainedMarks) { this.obtainedMarks = obtainedMarks; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}