
package com.challenge.me.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class GithubRepositoryFilesInformationDto implements Serializable {

	private static final long serialVersionUID = -2475302157167494514L;
	private Map<String, Integer> totalNumberOfLines = new HashMap<>();
	private Map<String, Double> totalNumberOfBytes = new HashMap<>();

	public GithubRepositoryFilesInformationDto() {}

	public GithubRepositoryFilesInformationDto(final Map<String, Integer> totalNumberOfLines,
			final Map<String, Double> totalNumberOfBytes) {
		super();
		this.totalNumberOfLines = totalNumberOfLines;
		this.totalNumberOfBytes = totalNumberOfBytes;
	}

	public Map<String, Integer> getTotalNumberOfLines() {
		return totalNumberOfLines;
	}

	public void setTotalNumberOfLines(final Map<String, Integer> totalNumberOfLines) {
		this.totalNumberOfLines = totalNumberOfLines;
	}

	public Map<String, Double> getTotalNumberOfBytes() {
		return totalNumberOfBytes;
	}

	public void setTotalNumberOfBytes(final Map<String, Double> totalNumberOfBytes) {
		this.totalNumberOfBytes = totalNumberOfBytes;
	}
}
