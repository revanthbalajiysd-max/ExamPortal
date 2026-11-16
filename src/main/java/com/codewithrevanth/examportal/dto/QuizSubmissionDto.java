package com.codewithrevanth.examportal.dto;

import java.util.HashMap;
import java.util.Map;

public class QuizSubmissionDto {

    private Map<Long, String> answers = new HashMap<>();

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}