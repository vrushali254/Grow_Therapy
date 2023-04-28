package com.analytics.clients;

import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface WikiPageViewClient {
    final static String defaultProject = "/en.wikipedia";
    final static String defaultAccess = "/all-access";

    final static String defaultAgent = "/all-agents";
    @GET("metrics/pageviews/top" + defaultProject + defaultAccess + "/{year}/{month}/{day}")
   // @Headers("accept: application/json")
    CompletableFuture<TopArticlesResponse> getMostViewedArticles(@Path(value = "year") String year,
                                                                 @Path(value = "month") String month, @Path(value = "day") String day);

    @GET("metrics/pageviews/per-article" + defaultProject + defaultAccess + defaultAgent + "/{article}/{granularity}/{start}/{end}")
  //  @Headers("accept: application/json")
    CompletableFuture<ArticleResponse> getArticleViewCount(@Path(value = "article") String article, @Path(value = "granularity") String granularity,
                                                                  @Path(value = "start") String start, @Path(value = "end") String end);

}
