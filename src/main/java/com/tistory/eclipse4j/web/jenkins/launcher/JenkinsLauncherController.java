package com.tistory.eclipse4j.web.jenkins.launcher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JenkinsLauncherController {
	@RequestMapping("/")
	public String home() {
		return "Hello~";
	}
}
