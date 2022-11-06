package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
				.addMapping("/**")	//프로그램에서 제공하는 url
				.allowedOrigins("*");
//				.allowedOrigins("http://localhost:3000"); 허용할 출처를 명시, 가능하다면 목록으로 작성 
//				.allowedHeaders("*") //어떤 Header들을 허용할 것인가?
//				.allowedMethods("*"); //어떤 메서드를 허용할 것인가?
			}
		};
	}

}
