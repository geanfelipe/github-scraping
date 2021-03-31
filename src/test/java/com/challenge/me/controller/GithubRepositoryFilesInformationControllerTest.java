
package com.challenge.me.controller;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.challenge.me.dto.GithubRepositoryFilesInformationDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
public class GithubRepositoryFilesInformationControllerTest {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {

	}

	@Test
	public void whenValidRepositoryThenStatus200AndInformations() throws Exception {
		final ResponseEntity<GithubRepositoryFilesInformationDto> entity =
				testRestTemplate.getForEntity("http://localhost:" + mgt + "/info/geanfelipe/hello-microservice-message",
						GithubRepositoryFilesInformationDto.class);

		BDDAssertions.then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		final String actualResponseBody = objectMapper.writeValueAsString(entity.getBody());
		final String expectedResponseBody = objectMapper.writeValueAsString(getMockRepositoryFilesInformation());
		JSONAssert.assertEquals(actualResponseBody, expectedResponseBody, false);
	}

	@Test
	public void whenRepositoryDoestNotExistsThenStatus404() throws Exception {
		final ResponseEntity<GithubRepositoryFilesInformationDto> entity = testRestTemplate.getForEntity(
				"http://localhost:" + mgt + "/info/geanfelipe/hello-microservice-messageSSSSS",
				GithubRepositoryFilesInformationDto.class);

		BDDAssertions.then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	private GithubRepositoryFilesInformationDto getMockRepositoryFilesInformation() {
		final Map<String, Integer> totalNumberOfLines = new HashMap<>();
		final Map<String, Double> totalNumberOfBytes = new HashMap<>();
		totalNumberOfLines.put("java", 38);
		totalNumberOfLines.put("classpath", 31);
		totalNumberOfLines.put("gitignore", 1);
		totalNumberOfLines.put("xml", 62);
		totalNumberOfLines.put("md", 18);
		totalNumberOfLines.put("png", 0);
		totalNumberOfLines.put("project", 23);
		totalNumberOfLines.put("prefs", 14);

		totalNumberOfBytes.put("java", 1259.52);
		totalNumberOfBytes.put("classpath", 1198.08);
		totalNumberOfBytes.put("gitignore", 9.0);
		totalNumberOfBytes.put("xml", 2058.24);
		totalNumberOfBytes.put("md", 705.0);
		totalNumberOfBytes.put("png", 61849.6);
		totalNumberOfBytes.put("project", 555.0);
		totalNumberOfBytes.put("prefs", 474.0);
		return new GithubRepositoryFilesInformationDto(totalNumberOfLines, totalNumberOfBytes);
	}

}
