package com.mission.cure.api.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mission.cure.api.entity.Article;
import com.mission.cure.api.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	/**
	 * 
	 * Saves article object to database.
	 * 
	 * @param Article object to save
	 * @return Article persisted article
	 * 
	 **/
	public Article saveArticle(Article article) {

		return articleRepository.save(article);
	}

	/**
	 * 
	 * Get single article from database by its id.
	 * 
	 * @param id to search for
	 * @Article retrieved article
	 * 
	 **/
	public Article getArticle(long id) {
		Optional<Article> opt = articleRepository.findById(id);
		Article article = opt.orElse(null);
		
		return article;
	}

	/**
	 * 
	 * Get all articles from database.
	 * 
	 * @return List<Article> list with retrieved articles
	 * 
	 **/
	public List<Article> getArticles() {
		return articleRepository.findAll();
	}

}
