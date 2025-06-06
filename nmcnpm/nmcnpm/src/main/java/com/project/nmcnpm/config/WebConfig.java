package com.project.nmcnpm.config; // Hoặc package config của bạn

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng CORS cho tất cả các đường dẫn trong ứng dụng
            .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500") // Danh sách các origin được phép.
                                                                         // Thêm các origin khác nếu cần, ví dụ: domain khi deploy.
                                                                         // Dùng "*" để cho phép tất cả (cẩn thận khi dùng trong production).
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Các phương thức HTTP được phép
            .allowedHeaders("*") // Cho phép tất cả các header trong request
            .allowCredentials(true) // Cho phép gửi cookie và thông tin xác thực qua CORS.
                                    // Nếu đặt là true, allowedOrigins không thể là "*" mà phải là danh sách cụ thể.
            .maxAge(3600); // Thời gian (giây) mà kết quả của preflight request được cache lại bởi trình duyệt.
    }
}