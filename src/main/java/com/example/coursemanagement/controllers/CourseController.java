package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService cs;

    //Đọc
    @GetMapping
    public String courseManagementPage(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        Page<Course> coursePage = cs.getAllCourse(page);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());
        return "CourseManagement";
    }

    //Tìm kiếm
    @GetMapping("/search")
    public String searchCourse(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<Course> coursePage =
                cs.searchCourse(keyword, page);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("keyword", keyword);

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
            BindingResult result,
            @RequestParam("file")MultipartFile file,
            Model model) {

        if (result.hasErrors()) {
            return "FormCreateCourse";
        }

        try {
            cs.addCourse(course, file);

            return "redirect:/courses";

        } catch (RuntimeException ex) {

            model.addAttribute("fileError", ex.getMessage());

            return "FormCreateCourse";
        }
    }

    //Xóa
    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        Course c = cs.getCourseById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        model.addAttribute("course", c);
        return "FormConfirmDelete";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
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
    public String saveUpdate(
            @Valid
            @ModelAttribute("updateCourse") Course course,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model) {
        if (result.hasErrors()) {
            return "FormUpdateCourse";
        }
        try {
            cs.addCourse(course, file);

            return "redirect:/courses";

        } catch (RuntimeException ex) {

            model.addAttribute("fileError", ex.getMessage());

            return "FormUpdateCourse";
        }
    }
}
