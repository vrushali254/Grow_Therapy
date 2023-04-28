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

* Pre-reqs:
  * IDE: IntelliJ IDEA 
  * Maven plugin for IntelliJ: https://plugins.jetbrains.com/plugin/7179-maven-helper
  * Java JDK 20
  *
Please clone the repository using:
1. git clone https://github.com/vrushali254/Grow_Therapy.git
2. Load the project in IntelliJ:
   * Go to File > Open > Select Grow_Therapy
3. Import project dependencies using Maven
   *  Right-click on the pom.xml > Run Maven > Re-import 
   * ![Screenshot 2023-04-28 at 11.59.56 AM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2F5g%2Fr17cg8c12zq7pnkdv4h0j4s40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_F5L45C%2FScreenshot%202023-04-28%20at%2011.59.56%20AM.png)
4. Run AnalyticsApplication.java
![Screenshot 2023-04-28 at 12.01.14 PM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2F5g%2Fr17cg8c12zq7pnkdv4h0j4s40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_JWHHCX%2FScreenshot%202023-04-28%20at%2012.01.14%20PM.png)

5. The application should start running on port 8081
![Screenshot 2023-04-28 at 12.03.27 PM.png](..%2FScreenshot%202023-04-28%20at%2012.03.27%20PM.png)


Testing the application:

Swagger-UI has been integrated with the application.

You can test the APIs using:
http://localhost:8081/swagger-ui/index.html#/

The results directory contains the screenshots of all the valid responses from the APIs. 