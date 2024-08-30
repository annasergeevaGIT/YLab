package org.example.config;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializes the Spring MVC application.
 */
@EnableWebMvc
public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };  // Load AppConfig
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;  // No additional servlet config classes
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };  // Map the dispatcher servlet to "/"
    }
}
