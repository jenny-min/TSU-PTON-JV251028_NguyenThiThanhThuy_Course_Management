package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService cs;

    @GetMapping
    public String courseManagementPage(Model model) {
        model.addAttribute("courses", cs.getAllCourse());
        return "CourseManagement";
    }

    @GetMapping("/create")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());
        return "CreateCourse";
    }

    @PostMapping("/save")
    public String saveNewCourse(
            @Valid
            @ModelAttribute("course") Course course,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/courses/create";
        }

        cs.saveCourse(course);

        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourseById(@PathVariable Long id) {
        cs.deleteCourseById(id);
        return "redirect:/courses";
    }
}
