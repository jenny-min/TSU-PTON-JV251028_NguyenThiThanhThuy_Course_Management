package com.example.coursemanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên khóa học không được để trống")
    @Size(min = 5, max = 100, message = "Tên khóa học phải từ 5 đến 100 ký tự")
    private String courseName;

    @NotBlank(message = "Tên giảng viên không được để trống")
    private String instructor;

    @Min(value = 1, message = "Thời lượng phải lớn hơn 0")
    @Max(value = 499, message = "Thời lượng phải nhỏ hơn 500")
    private int duration;

    private String thumbnail;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructor='" + instructor + '\'' +
                ", duration=" + duration +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
