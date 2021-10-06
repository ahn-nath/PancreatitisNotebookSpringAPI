package com.mission.cure.api.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mission.cure.api.entity.Article;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Specifies methods used to obtain and modify article related information which is
 * stored in the database.
 */
@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
