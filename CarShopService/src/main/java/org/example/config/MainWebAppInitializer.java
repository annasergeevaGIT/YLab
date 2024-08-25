package org.example.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@EnableWebMvc
public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
        implements WebApplicationInitializer {

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


    protected DispatcherServlet createDispatcherServlet(AnnotationConfigWebApplicationContext context) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}

