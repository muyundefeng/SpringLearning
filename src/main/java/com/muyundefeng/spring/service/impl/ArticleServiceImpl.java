package com.muyundefeng.spring.service.impl;

import com.muyundefeng.spring.Utils.HashUtils;
import com.muyundefeng.spring.dao.ArticleDAO;
import com.muyundefeng.spring.entity.Article;
import com.muyundefeng.spring.service.ArticleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by lisheng on 17-2-24.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDAO articleDAO;

    @SuppressWarnings("Since15")
    @Override
    public String getAllArticle(String perCount, String pageIndex) {
        List<Article> articleList = articleDAO.getAllArticles();
        articleList.sort(new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                Date articleA = o1.getCreateTime();
                Date articleB = o2.getCreateTime();
                return articleA.after(articleB) ? 1 : 0;
            }
        });
        int perNum = Integer.parseInt(perCount);
        int index = Integer.parseInt(pageIndex);
        int resultTotal = articleList.size();
        String msg = "";
        JSONArray array = null;
        int maxPage = resultTotal % perNum == 0 ? resultTotal / perNum : resultTotal / perNum + 1;
        if (index > maxPage) {
            msg = "error page index";
            array = new JSONArray();
        } else {
            if (maxPage == index) {
                array = getArticle(articleList, (maxPage - 1) * perNum, resultTotal);
            } else {
                array = getArticle(articleList, perNum * (index - 1), perNum * index);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("resultTotal", resultTotal);
        jsonObject.put("detail", array);
        return jsonObject.toString();
    }

    @Override
    public Article getArticleById(String title, String origina) {
        String Id = HashUtils.generateHash(title + origina);
        Article article = articleDAO.getArticleById(Id);
        return article;
    }

    @Override
    public String addArticle(String content, String title, String orgina) {
        String msg = "";
        if (articleDAO.addArticle(content, title, orgina) != 0) {
            msg = "add article successfully";
        } else
            msg = "add article error";
        return msg;
    }

    public JSONArray getArticle(List<Article> articles, int start, int end) {
        List<Article> articleList = articles.subList(start, end);
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for (Article article : articleList) {
            JSONObject json = new JSONObject();
            String title = article.getArticleTitle();
            String origina = article.getArticleOrgina();
            Date createTime = article.getCreateTime();
            json.put("title", title);
            json.put("origina", origina);
            json.put("createTime", createTime);
            jsonArray.put(index, json);
        }
        return jsonArray;
    }
}
