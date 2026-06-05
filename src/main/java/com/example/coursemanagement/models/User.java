package com.example.coursemanagement.models;

import com.example.coursemanagement.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên người dùng không được để trống")
    private String userName;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
