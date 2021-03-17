
package com.challenge.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.me.business.GithubRepositoryFilesInformationBusiness;
import com.challenge.me.dto.GithubRepositoryFilesInformationDto;

@RestController
public class GithubRepositoryFilesInformationController {

	@Autowired
	private GithubRepositoryFilesInformationBusiness repositoryFilesInformationBusiness;

	@GetMapping("/info/{owner}/{repository}")
	public ResponseEntity<GithubRepositoryFilesInformationDto> gitHubInfo(@PathVariable("owner") final String owner,
			@PathVariable("repository") final String repository) {
		final GithubRepositoryFilesInformationDto filesInformationDto =
				repositoryFilesInformationBusiness.getInformationsFor(owner, repository);
		return new ResponseEntity<>(filesInformationDto, HttpStatus.OK);
	}
}
