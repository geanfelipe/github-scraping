
package com.challenge.me.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.me.scraping.GithubRepositoryScraper;
import com.challenge.me.service.GithubService;

/*
 * This is a simple java class that works like a facade interface for RespositoryScrapper
 */
@Component
public class GithubRepositoryScraperHelper {

	@Autowired
	private GithubService githubService;

	public GithubRepositoryScraper create() {
		final Map<String, Integer> totalNumberOfLines = new HashMap<>();
		final Map<String, Double> totalNumberOfBytes = new HashMap<>();
		return GithubRepositoryScraper.create(githubService, totalNumberOfLines, totalNumberOfBytes);
	}
}
