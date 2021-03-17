
package com.challenge.me.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.challenge.me.business.GithubRepositoryFilesInformationBusiness;
import com.challenge.me.dto.GithubRepositoryFilesInformationDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GithubRepositoryFilesInformationController.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class GithubRepositoryFilesInformationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private GithubRepositoryFilesInformationBusiness githubRepositoryFilesInformationBusiness;

	@BeforeEach
	public void beforeEach() {
		Mockito.when(githubRepositoryFilesInformationBusiness
				.getInformationsFor(ArgumentMatchers.nullable(String.class), ArgumentMatchers.nullable(String.class)))
				.thenReturn(getMockRepositoryFilesInformation());
	}

	@Test
	public void shouldReturnDetailsAndStatus200() throws Exception {
		final MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders
						.get("/info/{owner}/{repository}", "geanfelipe", "hello-microservice-message")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		final String actualResponseBody = mvcResult.getResponse().getContentAsString();
		final String expectedResponseBody = objectMapper.writeValueAsString(getMockRepositoryFilesInformation());

		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
	}

	private GithubRepositoryFilesInformationDto getMockRepositoryFilesInformation() {
		final Map<String, Integer> totalNumberOfLines = new HashMap<>();
		final Map<String, Double> totalNumberOfBytes = new HashMap<>();
		totalNumberOfLines.put("java", 38);
		totalNumberOfBytes.put("java", 1259.52d);
		return new GithubRepositoryFilesInformationDto(totalNumberOfLines, totalNumberOfBytes);
	}

}
