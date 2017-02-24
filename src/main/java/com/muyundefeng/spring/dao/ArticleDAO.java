package com.muyundefeng.spring.dao;

import com.muyundefeng.spring.entity.Article;

import java.util.List;

/**
 * Created by lisheng on 17-2-24.
 */
public interface ArticleDAO {

    public Article getArticleById(String articleId);

    public List<Article> getAllArticles();

    public int addArticle(String content, String title, String orgina);
}
