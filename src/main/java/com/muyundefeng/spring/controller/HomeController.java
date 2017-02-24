package com.muyundefeng.spring.controller;

/**
 * Created by lisheng on 17-2-24.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muyundefeng.spring.entity.Article;
import com.muyundefeng.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    ArticleService articleService;

    /**
     * http://host:port/getArticleList?perCount={perCount}&pageIndex={pageIndex}
     *
     * @param response
     * @param perCount
     * @param pageIndex
     * @return
     */
    @SuppressWarnings("Since15")
    @RequestMapping("/getArticleList")
    @ResponseBody
    public String getArticleList(HttpServletResponse response, @RequestParam("perCount") String perCount, @RequestParam("pageIndex") String pageIndex) {
        response.setContentType("application/json;charset=utf-8");
        if (perCount == null || perCount.length() == 0
                || pageIndex == null || pageIndex.length() == 0) {
            return "error page Index";
        }
        return articleService.getAllArticle(perCount, pageIndex);
    }

    /**
     * url:http://host:port/addArticle
     *
     * @param body {
     *             "title":"旋转字符串",
     *             "content":"题目描述给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。请写一个函数完成此功能，要求对长度的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)。分析与解法",
     *             "origina":"《编程之法》"
     *             }
     * @return
     */
    @RequestMapping(value = "/addArticle", produces = "application/json")
    @ResponseBody
    public String addArticle(@RequestBody String body) {
        String msg = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> map = objectMapper.readValue(body, Map.class);
            String title = map.get("title").toString();
            String content = map.get("content").toString();
            String origina = map.get("origina").toString();
            articleService.addArticle(content, title, origina);
            msg = "success";
        } catch (IOException e) {
            e.printStackTrace();
            msg = "error";
        }
        return msg;
    }

    /**
     * http://host:port/readArticle?title={title}&origina={origina}
     * @param title
     * @param orgina
     * @param response
     * @return
     */
    @RequestMapping(value = "/readArticle")
    @ResponseBody
    public String readArticle(@RequestParam("title") String title, @RequestParam("origina") String orgina, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        if (title == null || title.length() == 0
                || orgina == null || orgina.length() == 0) {
            return "error page Index";
        }
        Article article = articleService.getArticleById(title, orgina);
        return article.getArticleContent();
    }
}
