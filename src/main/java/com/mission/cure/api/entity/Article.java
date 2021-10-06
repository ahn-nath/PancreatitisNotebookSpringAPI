package com.mission.cure.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue
	private Long id;

	@Nationalized
	private String title;

	private String featuredImageURL;

	@Nationalized
	@Column(columnDefinition = "TEXT")
	private String content;

	private String highlightedParagraphs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article() {

	}

	public Article(String title, String featuredImageURL, String content) {
		this.title = title;
		this.featuredImageURL = featuredImageURL;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFeaturedImageURL() {
		return featuredImageURL;
	}

	public void setFeaturedImageURL(String featuredImageURL) {
		this.featuredImageURL = featuredImageURL;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHighlightedParagraphs() {
		return highlightedParagraphs;
	}

	public void setHighlightedParagraphs(String highlightedParagraphs) {
		this.highlightedParagraphs = highlightedParagraphs;
	}
}
