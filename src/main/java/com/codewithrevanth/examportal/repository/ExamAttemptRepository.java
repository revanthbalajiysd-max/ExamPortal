package com.codewithrevanth.examportal.repository;

import com.codewithrevanth.examportal.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    Optional<ExamAttempt> findByUsernameAndQuizIdAndSubmitted(String username, Long quizId, boolean submitted);
}