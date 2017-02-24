package com.muyundefeng.spring.entity;

import java.util.Date;

public class Article {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.article_id
     *
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    private String articleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.article_title
     *
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    private String articleTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.create_time
     *
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.article_orgina
     *
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    private String articleOrgina;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.article_content
     *
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    private String articleContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.article_id
     *
     * @return the value of article.article_id
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.article_id
     *
     * @param articleId the value for article.article_id
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.article_title
     *
     * @return the value of article.article_title
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.article_title
     *
     * @param articleTitle the value for article.article_title
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.create_time
     *
     * @return the value of article.create_time
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.create_time
     *
     * @param createTime the value for article.create_time
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.article_orgina
     *
     * @return the value of article.article_orgina
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public String getArticleOrgina() {
        return articleOrgina;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.article_orgina
     *
     * @param articleOrgina the value for article.article_orgina
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public void setArticleOrgina(String articleOrgina) {
        this.articleOrgina = articleOrgina == null ? null : articleOrgina.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.article_content
     *
     * @return the value of article.article_content
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.article_content
     *
     * @param articleContent the value for article.article_content
     * @mbggenerated Fri Feb 24 16:38:45 CST 2017
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent == null ? null : articleContent.trim();
    }
}