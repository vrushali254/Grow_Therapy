package com.analytics.services;

import com.analytics.clients.WikiPageViewClientImplementation;
import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import com.analytics.utils.Granularity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class ArticlesManager {
       WikiPageViewClientImplementation wikiPageViewClient = WikiPageViewClientImplementation.getInstance();


    // Find the weekly/monthly most viewed articles
    public TopArticlesResponse findMostViewedArticlesByGranularity(Granularity granularity, String year, String month, String day) throws ExecutionException, InterruptedException, IOException {
        return wikiPageViewClient.findMostViewedArticles(granularity, year, month, day);
    }

    // Find the weekly/monthly view count for a given article

    public ArticleResponse getViewCountForArticleByGranularity(String article, Granularity granularity, String startDate) throws ExecutionException, InterruptedException, IOException {
        return wikiPageViewClient.getArticleViewCount(article, granularity, startDate);
    }

    // Find the date in a month with most views for a given article
    public ArticleResponse.ArticlePageView findDayWithMostViewsInMonth(String article, String startDate) throws IOException {
        return wikiPageViewClient.getMostViewedDateForArticle(article, startDate);

    }
}
