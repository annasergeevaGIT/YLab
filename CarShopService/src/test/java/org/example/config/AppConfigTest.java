package org.example.config;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.http.converter.HttpMessageConverter;

public class AppConfigTest {
	@Test
	public void configureMessageConverters() {
		AppConfig a = new AppConfig();
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		a.configureMessageConverters(converters);
	}
}
