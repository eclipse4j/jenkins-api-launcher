package com.tistory.eclipse4j.web.jenkins.launcher;

import lombok.Data;

@Data
public class JenkinsLauncher {

	private String launcherUrl;
	
	private String token;
	
	private String jenkinsApiTokenUser;
	
	private String jenkinsApiTokenKey;

	
	public String getBuildLauncherUrl(String jenkinsApiServerBaseUrl) {
		StringBuilder urlString = new StringBuilder();
		urlString.append(jenkinsApiServerBaseUrl);
		urlString.append(launcherUrl);
		urlString.append("/build?token=").append(token).append("&delay=0sec");
		return urlString.toString();
	}
}
