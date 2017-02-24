package com.muyundefeng.spring.service;

import com.muyundefeng.spring.entity.Article;

/**
 * Created by lisheng on 17-2-24.
 */
public interface ArticleService {

    public String getAllArticle(String perCount, String pageIndex);

    public Article getArticleById(String title, String origina);

    public String addArticle(String content, String title, String orgina);
}
