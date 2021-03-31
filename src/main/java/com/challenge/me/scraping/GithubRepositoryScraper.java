
package com.challenge.me.scraping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.challenge.me.dto.GithubRepositoryFilesInformationDto;
import com.challenge.me.service.GithubService;

/**
 * Execute web scraping techniques
 *
 * @author
 */
public class GithubRepositoryScraper {

	private final GithubService githubService;

	private final Map<String, Integer> totalNumberOfLines;
	private final Map<String, Double> totalNumberOfBytes;

	/**
	 * In order to create a information of total number of lines and total number of bytes for each calling then who
	 * calls it is responsible to inject the objects
	 *
	 * @param githubService
	 * @param totalNumberOfLines
	 * @param totalNumberOfBytes
	 * @return {@link GithubRepositoryScraper}
	 */
	public static GithubRepositoryScraper create(final GithubService githubService,
			final Map<String, Integer> totalNumberOfLines,
			final Map<String, Double> totalNumberOfBytes) {
		return new GithubRepositoryScraper(githubService, totalNumberOfLines, totalNumberOfBytes);
	}

	private GithubRepositoryScraper(final GithubService githubService,
			final Map<String, Integer> totalNumberOfLines,
			final Map<String, Double> totalNumberOfBytes) {
		this.githubService = githubService;
		this.totalNumberOfBytes = totalNumberOfBytes;
		this.totalNumberOfLines = totalNumberOfLines;
	}

	public GithubRepositoryScraper process(final String url) {
		runPrinciple(url);
		return this;
	}

	public GithubRepositoryFilesInformationDto getInformation() {
		return new GithubRepositoryFilesInformationDto(totalNumberOfLines, totalNumberOfBytes);
	}

	private void runPrinciple(final String repositoryUrl) {
		final String htmlPage = githubService.getPage(repositoryUrl);
		final List<String> fileUrls = getFileUrls(htmlPage);
		if (fileUrls.isEmpty()) {
			final String fileExtension = getFileExtension(htmlPage);
			final Integer numberOfLines = getNumberOfLines(htmlPage);
			final double fileSize = getFileSize(htmlPage);
			totalNumberOfLines.merge(fileExtension, numberOfLines, Integer::sum);
			totalNumberOfBytes.merge(fileExtension, fileSize, Double::sum);
		} else {
			fileUrls.parallelStream().forEach(this::runPrinciple);
		}
	}

	private Integer getNumberOfLines(final String html) {
		final Pattern p = Pattern.compile(" \\d+ lines");
		final Matcher m = p.matcher(html);
		int numberOfLines = 0;
		while (m.find()) {
			numberOfLines += Integer.valueOf(m.group(0).replaceAll("\\D+", ""));
		}
		return numberOfLines;

	}

	private List<String> getFileUrls(final String html) {
		final Pattern p = Pattern.compile("<a class=\"js-navigation-open Link--primary\" .* href=\"(.*?)\">");
		final Matcher m = p.matcher(html);
		final List<String> resourceUrls = new ArrayList<>();
		while (m.find()) {
			resourceUrls.add(m.group(1));
		}
		return resourceUrls;
	}

	private String getFileExtension(final String html) {
		final Pattern p = Pattern.compile("<strong class=\"final-path\">(.*?)</strong>");
		final Matcher m = p.matcher(html);
		while (m.find()) {
			final String fileName = m.group(1);
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return "";
	}

	private double getFileSize(final String html) {
		final Pattern p = Pattern.compile("\\d[0-9]*\\.?[0-9]* (KB|MB|GB|Bytes)");
		final Matcher m = p.matcher(html);
		while (m.find()) {
			final String[] sizeAmount = m.group(0).split(" ");
			final Double fileSize = Double.valueOf(sizeAmount[0]);
			final String byteScale = sizeAmount[1];
			if (byteScale.equals("KB")) {
				return fileSize * 1024;
			} else if (byteScale.equals("MB")) {
				return fileSize * 1024 * 1024;
			} else if (byteScale.equals("GB")) {
				return fileSize * 1024 * 1024 * 1024;
			} else {
				return fileSize;
			}
		}
		return 0d;
	}

}
