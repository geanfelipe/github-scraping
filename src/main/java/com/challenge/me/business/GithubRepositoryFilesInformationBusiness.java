
package com.challenge.me.business;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.challenge.me.dto.GithubRepositoryFilesInformationDto;
import com.challenge.me.helper.GithubRepositoryScraperHelper;

@Component
public class GithubRepositoryFilesInformationBusiness {

	private static final Logger LOGGER = LoggerFactory.getLogger(GithubRepositoryFilesInformationBusiness.class);

	@Autowired
	private GithubRepositoryScraperHelper repositoryScraper;

	public GithubRepositoryFilesInformationDto getInformationsFor(final String owner, final String repository) {
		final String repositoryUrl = String.format("/%s/%s", owner, repository);
		return repositoryScraper.create().process(repositoryUrl).getInformation();
	}

	@Scheduled(cron = "0 15 23 * * *")
	@CacheEvict(value = "pageHtml", allEntries = true)
	public void flushCache() {
		LOGGER.info("Flush Cache {}", LocalDateTime.now());
	}
}
