package com.muyundefeng.spring.dao.impl;

import com.muyundefeng.spring.Utils.HashUtils;
import com.muyundefeng.spring.dao.ArticleDAO;
import com.muyundefeng.spring.entity.Article;
import com.muyundefeng.spring.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by lisheng on 17-2-24.
 */
@Repository
public class ArticleDAOImpl implements ArticleDAO {

    @Autowired
    ArticleMapper mapper;

    @Override
    public Article getArticleById(String articleId) {
        return mapper.selectByPrimaryKey(articleId);
    }

    @Override
    public List<Article> getAllArticles() {
        return mapper.selectAll();
    }

    @Override
    public int addArticle(String content, String title, String orgina) {
        Article article = new Article();
        article.setCreateTime(new Date());
        article.setArticleContent(content);
        article.setArticleId(HashUtils.generateHash(title + orgina));
        article.setArticleTitle(title);
        article.setArticleOrgina(orgina);
        mapper.insert(article);
        return 0;
    }
}
