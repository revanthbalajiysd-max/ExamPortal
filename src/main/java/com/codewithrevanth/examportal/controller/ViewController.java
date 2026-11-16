package com.codewithrevanth.examportal.controller;

import com.codewithrevanth.examportal.dto.QuizSubmissionDto;
import com.codewithrevanth.examportal.model.Category;
import com.codewithrevanth.examportal.model.Question;
import com.codewithrevanth.examportal.model.Quiz;
import com.codewithrevanth.examportal.model.QuizResult;
import com.codewithrevanth.examportal.service.CategoryService;
import com.codewithrevanth.examportal.service.QuestionService;
import com.codewithrevanth.examportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("category", new Category());
        model.addAttribute("question", new Question());
        model.addAttribute("quizzes", quizService.getQuizzes());
        model.addAttribute("categories", categoryService.getCategories());
        return "admin-dashboard";
    }

    @PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/view/admin";
    }

    @PostMapping("/admin/quizzes/save")
    public String saveQuiz(@ModelAttribute("quiz") Quiz quiz) {
        quizService.addQuiz(quiz);
        return "redirect:/view/admin";
    }

    @GetMapping("/admin/quiz/{qId}/questions")
    public String viewQuizQuestions(@PathVariable("qId") Long qId, Model model) {
        Quiz quiz = quizService.getQuiz(qId);
        if (quiz == null) {
            return "redirect:/view/admin";
        }

        Question question = new Question();

        model.addAttribute("quiz", quiz);
        model.addAttribute("qId", qId);
        model.addAttribute("question", question);
        model.addAttribute("questions", questionService.getQuestionsOfQuiz(qId));

        return "admin-questions";
    }

    @PostMapping({"/admin/questions/save", "/admin/quiz/{qId}/questions/save"})
    public String saveQuestion(
            @PathVariable(value = "qId", required = false) Long pathQId,
            @RequestParam(value = "qId", required = false) Long paramQId,
            @ModelAttribute("question") Question question) {

        Long qId = (pathQId != null) ? pathQId : paramQId;

        if (qId == null) {
            return "redirect:/view/admin";
        }

        Quiz quiz = quizService.getQuiz(qId);
        if (quiz != null) {
            question.setQuiz(quiz);
            questionService.addQuestion(question);
        }
        return "redirect:/view/admin/quiz/" + qId + "/questions";
    }

    @PostMapping("/admin/quiz/{qId}/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable("qId") Long qId,
                                 @PathVariable("questionId") Long questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/view/admin/quiz/" + qId + "/questions";
    }

    @GetMapping("/student")
    public String studentRoot() {
        return "redirect:/view/student/dashboard";
    }

    @GetMapping("/student/login")
    public String studentLogin() {
        return "login";
    }

    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model) {
        model.addAttribute("quizzes", quizService.getQuizzes());
        return "student-dashboard";
    }

    @GetMapping("/student/quiz/{qId}")
    public String startStudentQuiz(@PathVariable("qId") Long qId, Model model) {
        Quiz quiz = quizService.getQuiz(qId);
        if (quiz == null) {
            return "redirect:/view/student/dashboard";
        }

        List<Question> questions = new ArrayList<>(questionService.getQuestionsOfQuiz(qId));

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("submission", new QuizSubmissionDto());
        return "take-quiz";
    }

    @PostMapping("/quiz/eval/{qId}")
    public String evaluateQuiz(@PathVariable("qId") Long qId,
                               @ModelAttribute("submission") QuizSubmissionDto submission,
                               Principal principal,
                               Model model) {
        Quiz quiz = quizService.getQuiz(qId);
        if (quiz == null) {
            return "redirect:/view/student/dashboard";
        }

        String username = (principal != null) ? principal.getName() : "Guest Student";
        QuizResult result = quizService.evaluateAndSaveQuiz(qId, submission.getAnswers(), username);

        model.addAttribute("result", result);
        model.addAttribute("quiz", result.getQuiz());
        return "quiz-result";
    }

    @GetMapping("/quiz/{qId}")
    public String takeQuiz(@PathVariable("qId") Long qId) {
        return "redirect:/view/student/quiz/" + qId;
    }
}