package com.muyundefeng.spring.controller;

/**
 * Created by lisheng on 17-2-24.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
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

}
