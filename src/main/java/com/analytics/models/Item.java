package com.analytics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    @SerializedName("project")
    @Expose
    public String project;
    @SerializedName("access")
    @Expose
    public String access;
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("month")
    @Expose
    public String month;
    @SerializedName("day")
    @Expose
    public String day;
    @SerializedName("articles")
    @Expose
    public List<Article> articles;

    public Item(String project, String access, String year, String month, String day) {
        this.setProject(project);
        this.setAccess(access);
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
    }
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}