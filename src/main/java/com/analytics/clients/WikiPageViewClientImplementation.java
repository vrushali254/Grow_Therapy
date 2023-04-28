package com.analytics.clients;

import com.analytics.models.Article;
import com.analytics.models.Item;
import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import com.analytics.utils.DateConverter;
import com.analytics.utils.Granularity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class WikiPageViewClientImplementation {
    static WikiPageViewClientImplementation wikiPageViewClientImplementation;
    private static WikiPageViewClient pageViewClient;

    public WikiPageViewClientImplementation() {
       // JavaNetUriAccess JacksonConverterFactory;
        Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(" https://wikimedia.org/api/rest_v1/")
                .addConverterFactory(GsonConverterFactory.create())
               .build();
       pageViewClient = retrofit.create(WikiPageViewClient.class);
   }

   public static WikiPageViewClientImplementation getInstance() {
       if(wikiPageViewClientImplementation==null) {
           return new WikiPageViewClientImplementation();
       }
       return wikiPageViewClientImplementation;
   }

    public static WikiPageViewClient getClient() {
        return pageViewClient;
    }


    public TopArticlesResponse findMostViewedArticles(Granularity granularity, String year, String month, String date, int limit) throws ExecutionException, InterruptedException, IOException {
        System.out.println("date:" + date);
        if(granularity.equals(Granularity.MONTHLY)) {
            return findMostViewedArticlesByMonth(year, month, "all-days");
        } else {
            return findMostViewedArticlesByWeek(year, month, date, limit);
        }
    }

    public TopArticlesResponse findMostViewedArticlesByMonth(String year, String month, String date) throws ExecutionException, InterruptedException, IOException {
        System.out.println("date:" + date);
        CompletableFuture<TopArticlesResponse> call = pageViewClient.getMostViewedArticles(year, month, date);
        TopArticlesResponse response = call.get();
        System.out.println("daily " + response.toString());
        return response;
    }

    public TopArticlesResponse findMostViewedArticlesByWeek(String year, String month, String date, int limit) throws ExecutionException, InterruptedException, IOException {
        System.out.println("date:" + date);
        // Get the dates for the whole week
        List<String> daysInWeek = DateConverter.getDaysInWeek(year, month, date);
        List<Article> articlesInWeek = new ArrayList<>();

        // Get top views for each day in the week
        for(String nextDate:daysInWeek) {
            String[] dateTokens = DateConverter.splitDate(nextDate);
            CompletableFuture<TopArticlesResponse> call = pageViewClient.getMostViewedArticles(dateTokens[0], dateTokens[1], dateTokens[2]);
            TopArticlesResponse response = call.get();
            System.out.println("Weekly: " + response.toString());
            articlesInWeek.addAll(response.getItems().get(0).getArticles());
        }

        List<Article> aggregatedArticles = aggregateWeeklyViews(articlesInWeek, limit);
        return formatArticles(aggregatedArticles, year, month, date);

    }

    public ArticleResponse getArticleViewCount(String article, Granularity granularity, String startDate) throws ParseException, ExecutionException, InterruptedException {
        if(granularity.equals(Granularity.MONTHLY)) {
            return getArticleViewCountByMonth(article, granularity, startDate);
        } else {
            return getArticleViewCountByWeek(article, startDate);
        }
    }

    public ArticleResponse getArticleViewCountByMonth(String article, Granularity granularity, String startDate) throws ExecutionException, InterruptedException {
        String endDate = DateConverter.addMonths(startDate, 1L);
        System.out.println("end: " + endDate);
        CompletableFuture<ArticleResponse> call = pageViewClient.getArticleViewCount(article, granularity.toString().toLowerCase(), startDate, endDate);
        ArticleResponse response  = call.get();
        return response;
    }

    public ArticleResponse getArticleViewCountByWeek(String article, String startDate) throws ExecutionException, InterruptedException {
        String endDate = DateConverter.addWeeks(startDate, 1L);

        CompletableFuture<ArticleResponse> call = pageViewClient.getArticleViewCount(article, "daily", startDate, endDate);
        ArticleResponse response  = call.get();

        // Aggregate all the daily views into a single weekly view
        List<ArticleResponse.ArticlePageView> articlePageViews = response.getArticlePageViews();

        // Merge all view count in a cascading manner
        for(int i=1; i<articlePageViews.size(); i++) {
            ArticleResponse.ArticlePageView pageView = articlePageViews.get(i);
            Long prevViews = articlePageViews.get(i-1).getViews();
            Long currViews = articlePageViews.get(i).getViews();
            pageView.setViews(prevViews+currViews);
            articlePageViews.remove(i-1);
            i--;
        }
        return response;
    }

    public ArticleResponse.ArticlePageView getMostViewedDateForArticle(String article,  String startDate) throws ExecutionException, InterruptedException {
        String endDate = DateConverter.addMonths(startDate, 1L);

        CompletableFuture<ArticleResponse> call = pageViewClient.getArticleViewCount(article, "daily", startDate, endDate);
        ArticleResponse response  = call.get();

        // Aggregate all the daily views into a single weekly view
        List<ArticleResponse.ArticlePageView> articlePageViews = response.getArticlePageViews();

        // Find the date with the most views
        Long maxViews = Long.MIN_VALUE;
        int indexOfDayWithMaxViews=0;
        for(int i=0; i<articlePageViews.size(); i++) {
            ArticleResponse.ArticlePageView pageView = articlePageViews.get(i);
            Long currViews = pageView.getViews();
            if(currViews>maxViews) {
                maxViews = currViews;
                indexOfDayWithMaxViews = i;
            }
        }
        // Article at dayWithMaxView has the answer
        ArticleResponse.ArticlePageView topArticle = articlePageViews.get(indexOfDayWithMaxViews);

        return topArticle;
    }

    //Aggregate the article views in a week
    private List<Article> aggregateWeeklyViews(List<Article> articlesInWeek, int limit) {
        // Create a map of the article to its total weekly views
        Map<String, Long> articleViewMap = createArticleViewMap(articlesInWeek);
        List<Article> aggregatedArticles = new ArrayList<>();
        for(Map.Entry article:articleViewMap.entrySet()) {
            String articleId = (String) article.getKey();
            Long totalViews = (Long) article.getValue();
            aggregatedArticles.add(new Article(articleId,totalViews, 0L));
        }
        // Sort the articles in descending based on views.
        aggregatedArticles.sort((Article x, Article y) -> Long.compare(y.getViews(), x.getViews()));

        //Throttle the num of articles by limit
        if(aggregatedArticles.size()>=limit) {
            aggregatedArticles = aggregatedArticles.subList(0, limit);
        }

        // Add ranks to the top viewed articles
        rankMostViewedArticles(aggregatedArticles);

        return aggregatedArticles;
    }

    // Local helper functions
    private Map<String, Long> createArticleViewMap(List<Article> articlesInWeek) {
        Map<String, Long> articleViewMap = new HashMap<>();
        for(Article article: articlesInWeek) {
            String articleId = article.getArticleId();
            Long views = article.getViews();
            if(!articleViewMap.containsKey(articleId)) {
                articleViewMap.put(articleId, 0L);
            }
            articleViewMap.put(articleId, views+articleViewMap.get(articleId));
        }
        return articleViewMap;
    }

    private List<Article> rankMostViewedArticles(List<Article> aggregatedArticles) {
        // Add rank to each article
        for(int i=0; i<aggregatedArticles.size(); i++) {
            Article updateArticle = aggregatedArticles.get(i);
            updateArticle.setRank(Long.valueOf(i+1));
        }
        return aggregatedArticles;
    }

    private TopArticlesResponse formatArticles(List<Article> articles, String year, String month, String date) {
        TopArticlesResponse response = new TopArticlesResponse();
        response.setItems(new ArrayList<>());
        response.getItems().add(new Item(WikiPageViewClient.defaultProject, WikiPageViewClient.defaultAccess,  year, month, date));
        response.getItems().get(0).setArticles(articles);
        return response;
    }


}
