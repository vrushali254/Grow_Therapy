# Grow_Therapy

Assignment goals:

● Retrieve a list of the most viewed articles for a week or a month \
● For any given article, be able to get the view count of that specific article for a week or a month \
● Retrieve the day of the month, where an article got the most page views

Approach to solving the problem:

● Created 3 wrapper APIs that internally calls Wiki's Pageview APIs.
● The definitions of the 3 wrapper APIs:
GET /api/v1/articles/top/{granularity}/{year}/{month}/{day}
GET /api/v1/articles/pageviews/{articleId}/{granularity}/{start_date}
GET /articles/{articleId}/top/daily/{start_date}


* The web framework used was Spring Boot. The pkg manager was Maven. And the code is written in Java.

* The dir structure followed is based on MVC architecture:

  ├── com.analytics  \
  │   ├── controllers  -> MVC style controller to handle API request \
  │   ├── clients  -> Wrappers that call 3rd party APIs \
  │   ├── services -> Middle layer that maps an API request to it's function \
  │   ├── models  -> Data classes \
  │   ├── responses  -> Response schemas \
  │   ├── exceptions -> Exception handlers to return error responses \
  │   ├── utils  -> Shared util functions \
  ├── pom.xml -> Project dependencies \
 
* Unit tests are written for the wrapper class that calls the 3rd Party APIs. 
* They mainly test the happy paths for all the wrapper methods, invalid inputs, request failures.

Assumptions:
* It is assumed that the 3rd Party is setting a max limit (1000) on the number of 1000 most viewed articles
and a rate limit of 100 req/s on the view counts for an article.
If the user breaks the threshold, the 3rd Party APIs should handle it and send back an error response.

Running the application:


Testing the application:

Swagger-UI has been integrated with the application.

You can test the APIs using:
http://localhost:8081/swagger-ui/index.html#

The results directory contains the screenshots of all the valid responses from the APIs. 