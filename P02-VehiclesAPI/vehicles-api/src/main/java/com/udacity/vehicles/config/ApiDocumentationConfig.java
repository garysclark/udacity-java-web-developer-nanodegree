package com.udacity.vehicles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class ApiDocumentationConfig{
	@Bean
	public OpenAPI api() {
		return new OpenAPI()                
				.info(new Info().title("Vehicles Api")                
						.description("Vehicle management application")                             
						.version("v0.0.1")                
						.license(new License().name("Apache 2.0")));                                   
	}
}
