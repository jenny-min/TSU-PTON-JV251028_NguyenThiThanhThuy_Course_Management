package com.example.coursemanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("courses")
public class CourseManagementController {
    @GetMapping
    public String courseManagementPage(){
        return "CourseManagement";
    }
}
