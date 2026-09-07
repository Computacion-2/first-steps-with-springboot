package com.discography.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.config.AppConfig;

public class DemoApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		context.close();
	}

}
