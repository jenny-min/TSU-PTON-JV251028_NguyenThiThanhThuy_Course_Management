package com.example.coursemanagement.services;

import com.example.coursemanagement.models.Course;
import com.example.coursemanagement.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository cr;

    private static final String UPLOAD_DIR = "uploads";

    public Page<Course> getAllCourse(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return cr.findAll(pageable);
    }

    public Page<Course> searchCourse(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        if (keyword == null || keyword.trim().isEmpty()) {
            return cr.findAll(pageable);
        }

        return cr.searchCourse(keyword, pageable);
    }

    public Course addCourse(Course course, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {

                String fileName = file.getOriginalFilename();

                // Validate
                String lowerCaseName = fileName.toLowerCase();

                if (!lowerCaseName.endsWith(".jpg")
                        && !lowerCaseName.endsWith(".jpeg")
                        && !lowerCaseName.endsWith(".png")) {

                    throw new RuntimeException("Chỉ cho phép file jpg, jpeg, png");
                }

                // Tạo thư mục uploads nếu chưa tồn tại
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Tạo tên file mới
                String extension = fileName.substring(fileName.lastIndexOf("."));

                String newFileName = UUID.randomUUID() + extension;

                // Lưu file
                Files.copy(
                        file.getInputStream(), uploadPath.resolve(newFileName),
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Chỉ lưu tên file
                course.setThumbnail(newFileName);
            }

            return cr.save(course);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi upload file: " + e.getMessage());
        }
    }

    public void deleteCourseById(Long id) {
        Course course = cr.findById(id).orElseThrow(() ->
                new RuntimeException("Không tìm thấy khóa học"));

        //Xóa khóa học thì xóa luôn ảnh - nếu có
        if (course.getThumbnail() != null
                && !course.getThumbnail().isBlank()) {
            Path thumnailPath = Paths.get(UPLOAD_DIR, course.getThumbnail());

            try {
                Files.deleteIfExists(thumnailPath);
            } catch (IOException e) {
                throw new RuntimeException("Không thể xóa ảnh " + course.getThumbnail());
            }
        }
        cr.deleteById(id);
    }

    public Optional<Course> getCourseById(Long id) {
        return cr.findById(id);
    }
}
