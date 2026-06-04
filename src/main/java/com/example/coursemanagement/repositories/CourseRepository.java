package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
            SELECT c
            FROM Course c
            WHERE LOWER(c.courseName)
                  LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.instructor)
                  LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<Course> searchCourse(
            @Param("keyword") String keyword
    );
}
