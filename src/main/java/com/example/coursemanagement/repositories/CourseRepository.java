package com.example.coursemanagement.repositories;

import com.example.coursemanagement.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        select c
        from Course c
        where lower(c.courseName)
        like lower(concat('%', :keyword, '%'))
        or lower(c.instructor)
        like lower(concat('%', :keyword, '%'))
        """)
    Page<Course> searchCourse(
            @Param("keyword") String keyword,
            Pageable pageable);
}
