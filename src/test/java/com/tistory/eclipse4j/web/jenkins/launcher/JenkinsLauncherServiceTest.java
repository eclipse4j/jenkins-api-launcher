package com.tistory.eclipse4j.web.jenkins.launcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "development")
public class JenkinsLauncherServiceTest {

	@Autowired
	private JenkinsLauncherService service;

	@Value("${jenkins.api.server.api.user}")
	private String jenkinsApiTokenUser;

	@Value("${jenkins.api.server.api.token}")
	private String jenkinsApiTokenKey;

	@Test
	public void testExecuteJenkinsLauncher() throws Exception {
		JenkinsLauncher jenkinsLauncher = new JenkinsLauncher();
		jenkinsLauncher.setJenkinsApiTokenKey(jenkinsApiTokenKey);
		jenkinsLauncher.setJenkinsApiTokenUser(jenkinsApiTokenUser);
		jenkinsLauncher.setToken("jenkins-launcher-exsample_00");
		jenkinsLauncher.setLauncherUrl("/job/jenkins-launcher-exsample_00");
		service.execute(jenkinsLauncher);
	}

}
