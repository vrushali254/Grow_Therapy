# Grow_Therapy

## Assignment goals:

● Retrieve a list of the most viewed articles for a week or a month \
● For any given article, be able to get the view count of that specific article for a week or a month \
● Retrieve the day of the month, where an article got the most page views

## Approach to solving the problem:

● Created 3 wrapper APIs that internally calls Wiki's Pageview APIs.
● The definitions of the 3 wrapper APIs:

GET /api/v1/articles/top/{granularity}/{year}/{month}/{day}

GET /api/v1/articles/pageviews/{articleId}/{granularity}/{start_date}

GET /articles/{articleId}/top/daily/{start_date}

● The web framework used was Spring Boot. The pkg manager was Maven. And the code is written in Java.

● The DIR structure followed is based on MVC architecture:

  ├── com.analytics  \
  │   ├── controllers  -> MVC style controller to handle API request \
  │   ├── clients  -> Wrappers that call 3rd party APIs \
  │   ├── services -> Middle layer that maps an API request to it's function \
  │   ├── models  -> Data classes \
  │   ├── responses  -> Response schemas \
  │   ├── exceptions -> Exception handlers to return error responses \
  │   ├── utils  -> Shared util functions \
  ├── pom.xml -> Project dependencies \
 
● Unit tests are written for the wrapper class that calls the 3rd Party APIs. \
● They mainly test the happy paths for all the wrapper methods, invalid inputs, request failures. \
● The results directory contains the screenshots of all the valid responses from the APIs. \

## Assumptions:
* It is assumed that the 3rd Party is setting a max limit (1000) on the number of 1000 most viewed articles
and a rate limit of 100 req/s on the view counts for an article.
If the user breaks the threshold, the 3rd Party APIs should handle it and send back an error response.

## Running the application:

* Pre-reqs:
  * IDE: IntelliJ IDEA
  * Maven plugin for IntelliJ
  * Java 8+ supported
  
1.Please clone the repository using:
   git clone https://github.com/vrushali254/Grow_Therapy.git
2.Load the project in IntelliJ:
   * Go to File > Open > Select Grow_Therapy
3.Run AnalyticsApplication.java

<img width="660" alt="Screenshot 2023-04-28 at 12 07 30 PM" src="https://user-images.githubusercontent.com/22159825/235232526-4988ed71-74ed-4ea2-b196-728758f068cc.png">

5. The application should start running on port 8081

<img width="1492" alt="Screenshot 2023-04-28 at 12 03 27 PM" src="https://user-images.githubusercontent.com/22159825/235232329-2858a360-2df7-431a-8b5f-234fd6c5c0d5.png">

Trouble shooting:
In case, the dependecies are not automatically installed when the project was imported, use the following steps to re-trigger an import sync:

1. Import project dependencies using Maven
   *  Right-click on the pom.xml > Run Maven > Re-import 

<img width="400" alt="Screenshot 2023-04-28 at 12 07 07 PM" src="https://user-images.githubusercontent.com/22159825/235232474-c51d9b34-cb40-48a1-ab15-ac2fec0a12d6.png">

## Testing the application:

Swagger-UI has been integrated with the application.

You can test the APIs using:

http://localhost:8081/swagger-ui/index.html#/ 
