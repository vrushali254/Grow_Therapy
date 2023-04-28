package com.analytics.controllers;

import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import com.analytics.services.ArticlesManager;
import com.analytics.utils.Granularity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
@RequestMapping("api/v1")
public class PageviewsController {
    @Autowired
    ArticlesManager articlesManager;

    /**
     * GET /api/v1/articles/top/{granularity}/{year}/{month}/{day}
     * Returns the most viewed articles in a month/day/week
     *
     * @param granularity DAILY, WEEKLY, MONTHLY
     * @param year
     * @param month
     * @param day - not required, default value = all-days
     *
     * @return
     * 		200 - returns an array of most viewed articles
     * 		400, 404, 429 - possible response errors from the 3rd API
     */
    @RequestMapping(value={"/articles/top/{granularity}/{year}/{month}/{day}"}, method=RequestMethod.GET)
    public TopArticlesResponse getMostViewedArticles(@PathVariable(name="granularity", required = true) Granularity granularity,
                                                     @PathVariable(name="year", required = true) String year,
                                                     @PathVariable(name="month", required = true) String month,
                                                     @PathVariable(name="day", required = true) String day) throws RuntimeException, ExecutionException, InterruptedException, IOException {
        return articlesManager.findMostViewedArticlesByGranularity(granularity, year, month, day);
    }

    /**
     * GET /api/v1/articles/pageviews/{articleId}/{granularity}/{start_date}
     * Returns the views count for a particular article/page for a month/week
     *
     * @param articleId - Name of the article
     * @param granularity - WEEKLY, MONTHLY
     * @param start_date - ISO Format YYYYMMDD
     *
     * @return
     * 		200 - returns an array of page views for an article
     * 		400, 404, 429 - possible response errors from the 3rd API
     */
    @RequestMapping(value="/articles/{articleId}/pageviews/{granularity}/{start_date}", method=RequestMethod.GET)
    public ArticleResponse getViewCountForArticle(@PathVariable(name="articleId", required = true) String articleId,
                                                  @PathVariable(name="granularity", required = true) Granularity granularity,
                                                  @PathVariable(name="start_date", required = true) String start_date) throws RuntimeException, ExecutionException, InterruptedException, IOException {

        return articlesManager.getViewCountForArticleByGranularity(articleId, granularity, start_date);
    }

    /**
     * GET /articles/{articleId}/top/monthly/{start_date}
     * Returns the date with the most page views for an article in a month
     *
     * @param articleId - Name of the article
     * @param start_date - ISO Format YYYYMMDD
     *
     * @return
     * 		200 - returns an array of page views for an article
     * 		400, 404, 429 - possible response errors from the 3rd API
     */
    @RequestMapping(value = "/articles/{articleId}/top/monthly/{start_date}", method = RequestMethod.GET)
    public ArticleResponse.ArticlePageView getDayWithMostViewCountForArticle(
            @PathVariable(name="articleId", required = true) String articleId,
            @PathVariable(name="start_date", required = true) String start_date) throws RuntimeException, IOException {
        return articlesManager.findDayWithMostViewsInMonth(articleId, start_date);
    }


}
