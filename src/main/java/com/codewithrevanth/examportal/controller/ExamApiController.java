package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.model.ExamAttempt;
import com.codewithrevanth.examportal.repository.ExamAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exam")
public class ExamApiController {

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @GetMapping("/session/{quizId}")
    public ResponseEntity<?> getOrCreateSession(@PathVariable Long quizId, Principal principal) {
        String username = principal.getName();
        Optional<ExamAttempt> existingAttempt = attemptRepository.findByUsernameAndQuizIdAndSubmitted(username, quizId, false);

        if (existingAttempt.isPresent()) {
            return ResponseEntity.ok(existingAttempt.get());
        }

        ExamAttempt newAttempt = new ExamAttempt();
        newAttempt.setUsername(username);
        newAttempt.setQuizId(quizId);
        newAttempt.setRemainingSeconds(1800);
        attemptRepository.save(newAttempt);

        return ResponseEntity.ok(newAttempt);
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<?> submitAnswer(@RequestBody Map<String, Object> payload, Principal principal) {
        // Process individual question answers or periodic timer sync asynchronously
        Long quizId = Long.valueOf(payload.get("quizId").toString());
        Integer remainingSeconds = Integer.valueOf(payload.get("remainingSeconds").toString());

        // Update remaining time in database for crash recovery sync
        ExamAttempt attempt = attemptRepository.findByUsernameAndQuizIdAndSubmitted(principal.getName(), quizId, false)
                .orElseThrow(() -> new RuntimeException("Active exam session not found"));

        attempt.setRemainingSeconds(remainingSeconds);
        attemptRepository.save(attempt);

        return ResponseEntity.ok(Map.of("status", "success", "remainingSeconds", remainingSeconds));
    }
}