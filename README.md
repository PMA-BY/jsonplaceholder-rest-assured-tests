# json-placeholder-api-test
Java API tests for a subset of functionality of [jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com)
service.

## Scope
These tests are testing public API of [jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com) service
according to its documentation.

### List of methods in Scope:
| Method | Endpoint                |
|--------|-------------------------|
| GET    | /posts                  |
| GET    | /posts/`{id}`           |
| GET    | /posts/`{id}`/comments  |
| GET    | /comments?postId=`{id}` |
| POST   | /posts                  |
| PUT    | /posts/`{id}`           |
| PATCH  | /posts/`{id}`           |
| DELETE | /posts/`{id}`           |

_Please note, that aspects of implementation, such as headers returned by and/or required to be sent to API endpoints,
particular error codes - are either not being tested at all (headers), or are tested to minimal extent in accordance with common sense
(e.g., in response for incorrectly provided parameters of certain endpoints we may reasonably expect_
***HTTP 400*** _error as per HTTP protocol specification)._

Other test coverage limitations are mentioned in relevant test documentation.

## The Acceptance Criteria
The test task **Acceptance Criteria** are the following:
* Tests should be built with layered architecture (core, domain, tests levels)
* Tests should be created using either Rest Assured or Spring Rest Template or Apache Http Client.
* Tests have to include critical path tests validations both positive and negative (define a set of tests on your own). @Smoke
* Implemented tests should be readable with needed comments.
* Tests must be implemented so that they can be launched in parallel.
* Naming and Code Conventions should be followed – i.e. [javaguide](https://google.github.io/styleguide/javaguide.html) or any other.

**NB:** _Due to time constraints not all of them were covered_ 

## Design considerations
Because of limited scope of the project and the test task **Acceptance Criteria** the following design decisions/assumptions were made:
* There were no any dependency injection or Enterprise frameworks (Guice, Spring etc.) used to keep code simple.
* In a larger-scope project, API tests should use robust reporting / logging framework, such as Allure. Here we use
  SureFire + Java Logging API.
* All libraries used by tests are located in "test" folder because we don't plan to export them or re-use from other
  projects.
* We use a lot of "utility" classes with static methods to keep things simple (and because no dependency injection
  framework is being used).

## Approach
* The initial exploratory testing was made by `Insomnia REST Client`.
You can [download](https://insomnia.rest/products/insomnia) Insomnia and `import` the most recent Insomnia export from [/docs/insomnia/](/docs/insomnia/Insomnia_v4.json) project folder.
![Insomnia](/docs/images/insomnia.png)


## Failing and Broken tests
Some tests, implemented with "common sense" considerations in mind are failing at the moment of implementation,
others can't be properly implemented without knowing some application internals (e.g., tests for POST requests)

* Tests that are currently failing (by the time of implementation) because if of perceived application bugs are tagged
  as "failing".
* Tests that are currently failing (by the time of implementation) because of missing information (e.g., POST requests)
  are tagged as "broken".

## Running tests
### Preconditions
You will need properly setup JAVA 9 JDK and Maven 3+. All commands below should be run from project base folder.

### Running passing tests
By default, only tests that were passing when implemented (regression suite) are being run:
```
mvn test 
```
You will want to run tests this way from CI pipeline

### Running tests including failing ones
Maven profile "include-failing" controls running failing tests:
```
mvn test -P inlclude-failing 
``` 

### Running all tests including failing and broken ones
Maven profile "all-tests" controls allow running all tests, including failing and broken:
```
mvn test -P all-tests 
``` 

### Running against localhost
By default, tests are being run against production environment (https://jsonplaceholder.typicode.com)
To run them against different environments (e.g., localhost:8080), you can use (add as needed) relevant profiles.

Example:
```
mvn test -P local
```
will run tests against 'localhost:8080'

### Running in parallel
We can change the default `pom.xml` behavior `parallel:all` `threadCount:1` by using Maven's `-T` parameter which builds modules in `n` parallel threads.
```
mvn -T 4 test 
```
will run tests in 4 threads