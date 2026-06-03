package com.example.coursemanagement.services;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository cr;

    public List<Course> getAllCourse() {
        return cr.findAll();
    }

    public Course saveCourse(Course course) {
        Course newCourse = cr.save(course);
        return cr.save(course);
    }

    public void deleteCourseById(Long id) {
        cr.deleteById(id);
    }
}
