package com.analytics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("article")
    @Expose
    public String articleId;

    @SerializedName("views")
    @Expose
    public Long views;

    @SerializedName("rank")
    @Expose
    public Long rank;

    public Article(String articleId, Long views, Long rank) {
        this.articleId = articleId;
        this.views = views;
        this.rank = rank;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getViews() {
        return views;
    }

    public Long getRank() {
        return rank;
    }

}
