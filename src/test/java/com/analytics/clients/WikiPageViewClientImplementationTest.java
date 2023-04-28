package com.analytics.clients;

import com.analytics.responses.ArticleResponse;
import com.analytics.responses.TopArticlesResponse;
import com.analytics.utils.Granularity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class WikiPageViewClientImplementationTest {
    WikiPageViewClientImplementation wikiPageViewClient;
    @BeforeEach
    void setUp() {
        wikiPageViewClient = WikiPageViewClientImplementation.getInstance();
    }

    @Test
    void findMostViewedArticlesByMonth() {
        try {
            TopArticlesResponse res = wikiPageViewClient.findMostViewedArticles(Granularity.MONTHLY, "2016", "01", "01");
            assertNotNull(res);
            String dayModified = res.getItems().get(0).getDay();
            String mostViewed = res.getItems().get(0).getArticles().get(0).getArticleId();
            int articlesSize = res.getItems().get(0).getArticles().size();
            assertEquals("all-days", dayModified, "Day changes to all-days for monthly granularity but got " + dayModified);
            assertEquals("Main_Page", mostViewed, "Most viewed article in 2016 first week is Main_page but got" + mostViewed);
            assertEquals(1000, articlesSize, "Expected limit of size 1000");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findMostViewedArticlesByWeek() {
        try {
            TopArticlesResponse res = wikiPageViewClient.findMostViewedArticles(Granularity.WEEKLY, "2018", "01", "01");
            assertNotNull(res);
            String mostViewed = res.getItems().get(0).getArticles().get(0).getArticleId();
            assertEquals("Main_Page", mostViewed, "Most viewed article in 2018-01-01 first week is Main_page but got " + mostViewed);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    void getArticleViewCountByMonth() {
        try {
            ArticleResponse res = wikiPageViewClient.getArticleViewCountByMonth("Barak_Obama", Granularity.MONTHLY, "20180101");
            assertNotNull(res);
            Long views = res.getArticlePageViews().get(0).getViews();
            assertEquals(252L, views, "Total views for barak_obama in Jan 2018 are 252 but got " + views);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getArticleViewCountByWeek() {
        try {
            ArticleResponse res = wikiPageViewClient.getArticleViewCountByWeek("Barak_Obama", "20180101");
            assertNotNull(res);
            Long views = res.getArticlePageViews().get(0).getViews();
            assertEquals(49L, views, "Total views for barak_obama in 2018-01-01 first week are 49 but got " + views);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getMostViewedDateForArticle() {
        try {
            ArticleResponse.ArticlePageView res = wikiPageViewClient.getMostViewedDateForArticle("Barak_Obama", "20180101");
            assertNotNull(res);
            String mostViewedDate = res.getTimestamp();
            Long views = res.getViews();
            assertEquals("2018012400", mostViewedDate, "The date of most views is 2018-01-24 but got " + mostViewedDate);
            assertEquals(25L, views, "Most views for 1 day for barak_obama in Jan 2018 are 25 but got " + views);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void findMostViewedArticlesByMonthInvalidInput() {
            ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> wikiPageViewClient.findMostViewedArticles(Granularity.MONTHLY, "20163478", "01", "01"));
            assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void findMostViewedArticlesByWeekInvalidInput() {
           ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> wikiPageViewClient.findMostViewedArticles(Granularity.WEEKLY, "2018", "01", "03231"));
           assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getArticleViewCountByMonthInvalidInput() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> wikiPageViewClient.getArticleViewCountByMonth("Barak_Obama", Granularity.MONTHLY, "2018010132"));
        assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getArticleViewCountByMonthArticleNotFound() {
        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> wikiPageViewClient.getArticleViewCountByMonth("unknown_art", Granularity.MONTHLY, "20180101"));
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }

}