package com.codewithrevanth.examportal.dto;

import com.codewithrevanth.examportal.model.QuizResult;
import java.util.List;

public class QuizAnalyticsDto {

    private int totalAttempts;
    private double averageMarks;
    private double averagePercentage;
    private double highestMarks;
    private int passedCount;
    private int failedCount;
    private double passRate;
    private List<QuizResult> results;

    public int getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(int totalAttempts) { this.totalAttempts = totalAttempts; }

    public double getAverageMarks() { return averageMarks; }
    public void setAverageMarks(double averageMarks) { this.averageMarks = averageMarks; }

    public double getAveragePercentage() { return averagePercentage; }
    public void setAveragePercentage(double averagePercentage) { this.averagePercentage = averagePercentage; }

    public double getHighestMarks() { return highestMarks; }
    public void setHighestMarks(double highestMarks) { this.highestMarks = highestMarks; }

    public int getPassedCount() { return passedCount; }
    public void setPassedCount(int passedCount) { this.passedCount = passedCount; }

    public int getFailedCount() { return failedCount; }
    public void setFailedCount(int failedCount) { this.failedCount = failedCount; }

    public double getPassRate() { return passRate; }
    public void setPassRate(double passRate) { this.passRate = passRate; }

    public List<QuizResult> getResults() { return results; }
    public void setResults(List<QuizResult> results) { this.results = results; }
}