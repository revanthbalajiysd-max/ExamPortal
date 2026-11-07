package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.service.CategoryService;
import com.codewithrevanth.examportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("quizzes", quizService.getQuizzes());
        model.addAttribute("categories", categoryService.getCategories());
        return "admin-dashboard";
    }

    @PostMapping("/admin/quizzes/save")
    public String saveQuiz(@ModelAttribute("quiz") Quiz quiz) {
        quizService.addQuiz(quiz);
        return "redirect:/view/admin";
    }

    @GetMapping("/quiz/{qid}")
    public String takeQuiz(@PathVariable("qid") Long qid, Model model) {
        Quiz quiz = quizService.getQuiz(qid);
        if (quiz == null) {
            return "redirect:/view/admin"; // Or an error/not-found page
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());
        return "take-quiz";
    }
}