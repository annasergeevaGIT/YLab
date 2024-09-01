package org.example.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.http.converter.HttpMessageConverter;

import org.example.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

	@Test
	void testDataSource() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		DataSource dataSource = context.getBean(DataSource.class);

		assertNotNull(dataSource);
		assertTrue(dataSource instanceof DriverManagerDataSource);

		DriverManagerDataSource ds = (DriverManagerDataSource) dataSource;
		assertEquals("org.postgresql.Driver", ds.getDriverClassName());
		assertEquals("jdbc:postgresql://localhost:5432/your_database", ds.getUrl());
		assertEquals("your_username", ds.getUsername());
		assertEquals("your_password", ds.getPassword());

		context.close();
	}

	@Test
	void testJdbcTemplate() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

		assertNotNull(jdbcTemplate);
		assertTrue(jdbcTemplate.getDataSource() instanceof DriverManagerDataSource);

		DriverManagerDataSource dataSource = (DriverManagerDataSource) jdbcTemplate.getDataSource();
		assertEquals("org.postgresql.Driver", dataSource.getDriverClassName());
		assertEquals("jdbc:postgresql://localhost:5432/your_database", dataSource.getUrl());
		assertEquals("your_username", dataSource.getUsername());
		assertEquals("your_password", dataSource.getPassword());

		context.close();
	}

	@Test
	void testConfigureMessageConverters() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		WebMvcConfigurer webMvcConfigurer = context.getBean(AppConfig.class);

		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		webMvcConfigurer.configureMessageConverters(converters);

		assertFalse(converters.isEmpty());
		assertTrue(converters.get(0) instanceof MappingJackson2HttpMessageConverter);

		MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) converters.get(0);
		ObjectMapper objectMapper = converter.getObjectMapper();

		assertTrue(objectMapper.isEnabled(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT));

		context.close();
	}
}
