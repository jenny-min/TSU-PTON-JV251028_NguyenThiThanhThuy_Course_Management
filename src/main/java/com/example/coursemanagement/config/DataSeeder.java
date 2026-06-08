package com.example.coursemanagement.config;

import com.example.coursemanagement.enums.Role;
import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final CourseRepository cr;
    private final UserRepository ur;
    private final PasswordEncoder passwordEncoder;

    //User trống, tự động chèn dữ liệu vào DB
    @Override
    public void run(String... args) throws Exception {
        if (ur.count() == 0) {
            User admin = User.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("123123"))
                    .fullName("Administrator")
                    .email("admin@gmail.com")
                    .role(Role.ADMIN)
                    .build();

            ur.save(admin);

            User user = User.builder()
                    .userName("user")
                    .password(passwordEncoder.encode("111"))
                    .fullName("User1")
                    .email("user1@gmail.com")
                    .role(Role.USER)
                    .build();

            ur.save(user);
        }

        if (cr.count() == 0) {

            List<Course> courses = List.of(
                    new Course(null, "Java Core Fundamentals", "Nguyen Van A", 40, "java.jpg"),
                    new Course(null, "Spring Boot Beginner", "Tran Thi B", 60, "spring.jpg"),
                    new Course(null, "Spring Security", "Le Van C", 45, "security.jpg"),
                    new Course(null, "REST API Development", "Pham Van D", 50, "api.jpg"),
                    new Course(null, "Hibernate JPA Master", "Nguyen Thi E", 55, "hibernate.jpg"),
                    new Course(null, "Database Design", "Tran Van F", 35, "database.jpg"),
                    new Course(null, "MySQL Advanced", "Le Thi G", 30, "mysql.jpg"),
                    new Course(null, "Frontend HTML CSS", "Pham Thi H", 25, "htmlcss.jpg"),
                    new Course(null, "JavaScript Essentials", "Nguyen Van I", 40, "js.jpg"),
                    new Course(null, "ReactJS Complete", "Tran Thi K", 70, "react.jpg"),
                    new Course(null, "Docker Basics", "Le Van L", 20, "docker.jpg"),
                    new Course(null, "Git And GitHub", "Pham Van M", 15, "git.jpg"),
                    new Course(null, "Microservices Architecture", "Nguyen Thi N", 80, "microservice.jpg"),
                    new Course(null, "Unit Testing Java", "Tran Van O", 30, "testing.jpg"),
                    new Course(null, "AWS Cloud Fundamentals", "Le Thi P", 45, "aws.jpg"),
                    new Course(null, "Linux For Developers", "Pham Thi Q", 35, "linux.jpg"),
                    new Course(null, "Data Structures Java", "Nguyen Van R", 50, "dsa.jpg"),
                    new Course(null, "Algorithm Practice", "Tran Thi S", 55, "algorithm.jpg"),
                    new Course(null, "Fullstack Project", "Le Van T", 90, "fullstack.jpg"),
                    new Course(null, "Software Engineering", "Pham Van U", 65, "se.jpg")
            );

            cr.saveAll(courses);
        }
    }
}
