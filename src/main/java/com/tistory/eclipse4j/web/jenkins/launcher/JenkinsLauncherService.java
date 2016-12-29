package com.tistory.eclipse4j.web.jenkins.launcher;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JenkinsLauncherService {

	@Value("${jenkins.api.server.host}")
	private String jenkinsApiServerHost;
	
	@Value("${jenkins.api.server.scheme}")
	private String jenkinsApiServerScheme;
	
	@Value("${jenkins.api.server.port}")
	private int jenkinsApiServerPort;

	@Value("${jenkins.api.server.baseurl}")
	private String jenkinsApiServerBaseUrl;
	
	private HttpHost host;
	
	private CredentialsProvider credsProvider;

	@PostConstruct
	public void initializeProvider() {
		host = new HttpHost(jenkinsApiServerHost, jenkinsApiServerPort, jenkinsApiServerScheme);
		log.info(host.toURI());
		credsProvider = new BasicCredentialsProvider();
	}

	public int execute(final JenkinsLauncher jenkinsLauncher) {
		try {
			AuthCache authCache = login(jenkinsLauncher.getJenkinsApiTokenUser(), jenkinsLauncher.getJenkinsApiTokenKey());
			log.info("[JENKINS] AuthCache : {}, Launcher URL : {}", authCache, jenkinsLauncher.getBuildLauncherUrl(jenkinsApiServerBaseUrl));
			return execute(jenkinsLauncher.getBuildLauncherUrl(jenkinsApiServerBaseUrl), authCache);
		} catch (Exception e) {
			log.error("[JENKINS] JENKINS TASK를 실행할 수 없습니다. Message={}", e.getMessage(), e);
		}
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}


	private AuthCache login(String jenkinsApiTokenUser, String jenkinsApiTokenKey) throws ClientProtocolException, IOException {
		credsProvider.setCredentials(new AuthScope(jenkinsApiServerHost, jenkinsApiServerPort),
				new UsernamePasswordCredentials(jenkinsApiTokenUser, jenkinsApiTokenKey));
		log.info("jenkinsApiTokenUser={}, jenkinsApiTokenKey={}", jenkinsApiTokenUser, jenkinsApiTokenKey);
		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);
		
		return authCache;
	}

	private int execute(String urlString, AuthCache authCache) throws IOException, ClientProtocolException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAuthCache(authCache);
		URI uri = URI.create(urlString);
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse response = httpClient.execute(host, httpPost, localContext);
		log.info(IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()));
		return response.getStatusLine().getStatusCode();
	}
}
