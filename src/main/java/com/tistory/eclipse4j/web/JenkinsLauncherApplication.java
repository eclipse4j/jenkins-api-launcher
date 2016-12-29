package com.tistory.eclipse4j.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles(profiles = "production")
public class JenkinsLauncherApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsLauncherApplication.class, args);
	}
}
