package com.project.nmcnpm.config;

import com.project.nmcnpm.entity.Category;
import com.project.nmcnpm.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // <-- THÊM IMPORT NÀY

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
// KHUYẾN NGHỊ: Cấu hình CORS chính qua WebMvcConfigurer để bao quát các RestController thông thường.
// Nếu bạn muốn giữ lại các cấu hình riêng cho Spring Data REST, bạn có thể implements cả hai.
public class MyDataRestConfig implements WebMvcConfigurer, RepositoryRestConfigurer { // <-- Cập nhật interface

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    // --- CẤU HÌNH CORS CHO TẤT CẢ CÁC CONTROLLER (BAO GỒM USERCONTROLLER) ---
    @Override
    public void addCorsMappings(CorsRegistry cors) { // <-- Đây là phương thức chính để cấu hình CORS
        cors.addMapping(basePath + "/**") // Áp dụng cho tất cả các API dưới base path (ví dụ: /api/**)
            .allowedOrigins(theAllowedOrigins) // Lấy từ application.properties: http://127.0.0.1:5500
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Quan trọng: Bao gồm OPTIONS cho preflight
            .allowedHeaders("*") // Cho phép tất cả các header
            .allowCredentials(true) // RẤT QUAN TRỌNG: Cho phép gửi cookie và HTTP authentication
            .maxAge(3600); // Thời gian sống của kết quả pre-flight request
    }

    // --- CẤU HÌNH RIÊNG CHO SPRING DATA REST (nếu bạn vẫn muốn các hạn chế HTTP method cho Product/Category) ---
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Lưu ý: CorsRegistry 'cors' ở đây là một đối tượng riêng và thường
        // không cần cấu hình lại CORS nếu đã có addCorsMappings() ở trên.
        // Chủ yếu dùng để cấu hình exposure (disableHttpMethods, exposeIds).

        // Các phương thức HTTP không được hỗ trợ (chỉ cho phép GET để xem)
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PUT,
                HttpMethod.PATCH
        };

        // Vô hiệu hóa các phương thức POST, PUT, DELETE, PATCH cho Product và Category
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(Category.class, config, theUnsupportedActions);

        // Hiển thị IDs của các Entity trong phản hồi JSON
        exposeIds(config);
    }

    /**
     * Vô hiệu hóa các phương thức HTTP cụ thể cho một Entity
     * @param theClass Lớp Entity cần cấu hình
     * @param config Cấu hình RepositoryRest
     * @param theUnsupportedActions Mảng các HttpMethod không được phép
     */
    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    /**
     * Hiển thị ID của tất cả các Entity trong phản hồi REST
     * @param config Cấu hình RepositoryRest
     */
    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = new ArrayList<>();

        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}