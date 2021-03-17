
package com.challenge.me.service;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubService {

	private static final String GITHUB_DOMAIN = "https://github.com";

	/**
	 * Request web page html. The result is cached using Spring Cache
	 *
	 * @param endpoint
	 * @return {@link HtmlResponseDto}
	 */
	@Cacheable("pageHtml")
	public String getPage(final String endpoint) {
		final HttpHeaders headers = new HttpHeaders();
		headers.set("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
		final HttpEntity<String> entity = new HttpEntity<>(headers);

		final RestTemplate restTemplate = new RestTemplate();
		final URI uri = URI.create(GITHUB_DOMAIN + endpoint);
		final ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
}
