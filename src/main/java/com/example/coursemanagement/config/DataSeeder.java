package com.example.coursemanagement.config;

import com.example.coursemanagement.enums.Role;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.repositories.CourseRepository;
import com.example.coursemanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    }
}
