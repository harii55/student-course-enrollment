package com.enrollment.controller;

import com.enrollment.service.CourseService;
import com.enrollment.service.EnrollmentService;
import com.enrollment.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollments/list";
    }

    @GetMapping("/new")
    public String showEnrollForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("today", LocalDate.now());
        return "enrollments/form";
    }

    @PostMapping
    public String enroll(@RequestParam Long studentId,
                         @RequestParam Long courseId,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate enrollmentDate,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        try {
            enrollmentService.enroll(studentId, courseId, enrollmentDate);
            redirectAttributes.addFlashAttribute("successMessage", "Student enrolled successfully.");
            return "redirect:/enrollments";
        } catch (IllegalStateException | EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("today", LocalDate.now());
            return "enrollments/form";
        }
    }

    @GetMapping("/{id}/grade")
    public String showGradeForm(@PathVariable Long id, Model model) {
        model.addAttribute("enrollment", enrollmentService.findById(id));
        return "enrollments/grade-form";
    }

    @PostMapping("/{id}/grade")
    public String updateGrade(@PathVariable Long id,
                              @RequestParam String grade,
                              RedirectAttributes redirectAttributes) {
        enrollmentService.updateGrade(id, grade);
        redirectAttributes.addFlashAttribute("successMessage", "Grade updated successfully.");
        return "redirect:/enrollments";
    }

    @PostMapping("/unenroll")
    public String unenroll(@RequestParam Long studentId,
                           @RequestParam Long courseId,
                           RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.unenroll(studentId, courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Student unenrolled successfully.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/enrollments";
    }
}
