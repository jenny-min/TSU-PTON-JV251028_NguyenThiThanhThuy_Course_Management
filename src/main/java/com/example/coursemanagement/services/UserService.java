package com.example.coursemanagement.services;

import com.example.coursemanagement.enums.Role;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String fullName, String email) {
        // Kiểm tra xem username đã tồn tại chưa
        User existingUser = ur.findByUserName(username);
        if (existingUser != null) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        // Tạo user mới với role mặc định là USER
        User newUser = User.builder()
                .userName(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .email(email)
                .role(Role.USER)
                .build();

        return ur.save(newUser);
    }

    public User findByUsername(String username) {
        return ur.findByUserName(username);
    }
}
