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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Value("${spring.data.rest.base-path}")
    private String basePath;
    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    private EntityManager entityManager;
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PUT,
                HttpMethod.PATCH
        };
        disableHpptMethods(Product.class, config, theUnsupportedActions);
        disableHpptMethods(Category.class, config, theUnsupportedActions);
        exposeIds(config);
        cors.addMapping(basePath + "/**").allowedOrigins(theAllowedOrigins);
    }
    private static void disableHpptMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
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