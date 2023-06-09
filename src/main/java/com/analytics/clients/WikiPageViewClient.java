package com.analytics.clients;

import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WikiPageViewClient {
    String defaultProject = "/en.wikipedia";
    String defaultAccess = "/all-access";
    String defaultAgent = "/all-agents";
    @GET("metrics/pageviews/top" + defaultProject + defaultAccess + "/{year}/{month}/{day}")
   // @Headers("accept: application/json")
    Call<TopArticlesResponse> getMostViewedArticles(@Path(value = "year") String year,
                                                    @Path(value = "month") String month, @Path(value = "day") String day);

    @GET("metrics/pageviews/per-article" + defaultProject + defaultAccess + defaultAgent + "/{article}/{granularity}/{start}/{end}")
  //  @Headers("accept: application/json")
    Call<ArticleResponse> getArticleViewCount(@Path(value = "article") String article, @Path(value = "granularity") String granularity,
                                                                  @Path(value = "start") String start, @Path(value = "end") String end);

}
