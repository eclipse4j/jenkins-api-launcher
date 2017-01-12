package com.tistory.eclipse4j.web.jenkins.launcher;

import lombok.Data;

@Data
public class JenkinsLauncher {

	private String launcherUrl;
	
	private String token;	// Job Token
	
	private String jenkinsApiTokenUser;	// Jenkins User
	
	private String jenkinsApiTokenKey;	// Jenkins User Token 

	
	public String getBuildLauncherUrl(String jenkinsApiServerBaseUrl) {
		StringBuilder urlString = new StringBuilder();
		urlString.append(jenkinsApiServerBaseUrl);
		urlString.append(launcherUrl);
		urlString.append("/build?token=").append(token).append("&delay=0sec");
		return urlString.toString();
	}
}
