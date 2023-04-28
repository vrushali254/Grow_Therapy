package com.analytics.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("items")
    @Expose
    public List<ArticlePageView> articlePageViews;

    public List<ArticlePageView> getArticlePageViews()  { return articlePageViews; }

    public void setArticlePageViews(List<ArticlePageView> articlePageViews) {
        this.articlePageViews = articlePageViews;
    }
    public class ArticlePageView {
        @SerializedName("project")
        @Expose
        public String project;
        @SerializedName("granularity")
        @Expose
        public String granularity;
        @SerializedName("access")
        @Expose
        public String access;
        @SerializedName("agent")
        @Expose
        public String agent;
        @SerializedName("timestamp")
        @Expose
        public String timestamp;
        @SerializedName("article")
        @Expose
        public String articleId;
        @SerializedName("views")
        @Expose
        public Long views;

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getGranularity() {
            return granularity;
        }

        public void setGranularity(String granularity) {
            this.granularity = granularity;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

        public String getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public Long getViews() {
            return views;
        }

        public void setViews(Long views) {
            this.views = views;
        }
    }

}
