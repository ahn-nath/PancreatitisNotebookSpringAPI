package com.mission.cure.api.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.jsoup.safety.Cleaner;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mission.cure.api.entity.Article;
import com.mission.cure.api.service.ArticleService;

//@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping({ "/articles" })
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	static List<String> articleList = Arrays.asList(
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/do-i-have-chronic-pancreatitis",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/diagnostic-tests",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/overview",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/causes",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/symptoms",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/gastrointestinal",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/pain-management",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/pain",
			"https://empowerpatients.wixsite.com/chronic-pancreatitis/thriving");

	/**
	 * 
	 * Get test article for demo or testing purposes.
	 * 
	 * @return ResponseEntity<String>
	 * 
	 **/
	@GetMapping({ "/test/get" })
	public ResponseEntity<String> getTestArticle() throws IOException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

		// process article
		Article article = processArticle(articleList.get(0));
		String articleData = writer.writeValueAsString((Object) article);

		logger.info(String.format("Model we are sending %s", articleData));

		return new ResponseEntity<String>(articleData, HttpStatus.OK);
	}

	/**
	 * 
	 * Get single article by id.
	 * 
	 * @return ResponseEntity<Article> 
	 * @param String id of the article to get.
	 * 
	 **/
	@GetMapping({ "/get/{id}" })
	public ResponseEntity<Article> getArticle(@PathVariable String id) throws IOException {

		// get article by id
		Article article = articleService.getArticle(Long.valueOf(id));

		return new ResponseEntity<Article>(article, HttpStatus.OK);

	}

	/**
	 * 
	 * Get all articles from the database and save them to a list.
	 * 
	 * @return ResponseEntity<List<Article>> list of articles with HTTP response
	 * 
	 **/
	@GetMapping({ "/get" })
	public ResponseEntity<List<Article>> getAllArticles() throws IOException {

		// article list
		List<Article> articles = articleService.getArticles();

		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);

	}

	/**
	 * 
	 * Import all articles in articleList from the Wix site and save to the database
	 * after processing them.
	 * 
	 * 
	 **/
	@GetMapping({ "/import" })
	public void importAllArticles() throws IOException {

		// process articles and save to database
		for (String url : articleList) {
			processArticle(url);
		}

	}

	/**
	 * 
	 * Extract article attributes and return it to save it to database. Given an
	 * HTML document, it filters the body content by tag, replaces unwanted
	 * elements, and saves the article body first. It also extracts he article
	 * title, featured image and author.
	 * 
	 * @param String url to fectch article.
	 * @return Article processed article.
	 * 
	 **/
	private Article processArticle(String url) throws IOException {

		Document doc;
		doc = Jsoup.connect(url).get();

		// cleaning: select tags to keep, remove and replace and apply to HTML body
		Safelist sl = new Safelist();

		// keep listed HTML tags
		sl.addTags("main", "h1", "h2", "h3", "h4", "h5", "p", "b", "span");
		Cleaner cleaner = new Cleaner(sl);
		Document cleanDoc = cleaner.clean(doc);

		// replace tags
		Elements mainBody = cleanDoc.getElementsByTag("main");
		String body = mainBody.html().replaceAll("&nbsp;", "").replaceAll("<[/]?span[^>]*>", "").replaceAll("<[/]?a[^>]*>", "");

		// attributes
		String title = doc.title();
		String text = body;
		String imageURL = doc.select("meta[property=og:image]").first() != null
				? doc.select("meta[property=og:image]").first().attr("content")
				: null;

		// create new entity to persist object to database
		Article newArticle = articleService.saveArticle(new Article(title, imageURL, text));

		return newArticle;
	}

}
