package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService cs;

    //Đọc
    @GetMapping
    public String courseManagementPage(Model model) {
        model.addAttribute("courses", cs.getAllCourse());
        return "CourseManagement";
    }

    //Thêm
    @GetMapping("/create")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());
        return "FormCreateCourse";
    }

    @PostMapping()
    public String createCourse(
            @Valid
            @ModelAttribute("course") Course course,
            BindingResult result) {
        if (result.hasErrors()) {
            return "FormCreateCourse";
        }

        cs.addCourse(course);

        return "redirect:/courses";
    }

    //Xóa
    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        Course c = cs.getCourseById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        model.addAttribute("course", c);
        return "FormConfirmDelete";
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam Long id) {
        cs.deleteCourseById(id);
        return "redirect:/courses";
    }

    //Sửa
    @GetMapping("/update/{id}")
    public String updateCourseById(@PathVariable Long id, Model model) {
        Optional<Course> courseOpt = cs.getCourseById(id);
        courseOpt.ifPresent(course -> model.addAttribute("updateCourse", course));
        return "FormUpdateCourse";
    }

    @PostMapping("/save")
    public String saveUpdate(@ModelAttribute("updateCourse") Course course) {
        cs.addCourse(course);
        return "redirect:/courses";
    }
}
