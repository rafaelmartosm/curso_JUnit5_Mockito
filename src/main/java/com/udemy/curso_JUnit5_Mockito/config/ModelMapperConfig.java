package com.udemy.curso_JUnit5_Mockito.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
