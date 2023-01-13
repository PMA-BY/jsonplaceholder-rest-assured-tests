package com.typicode.jsonplaceholder.posts.tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.PostRequestBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.typicode.jsonplaceholder.env.Environment.getBaseUrl;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.oneOf;

/**
 * These tests cover "/posts" endpoint scenarios.
 * For other scope considerations please refer to README.md
 */

@DisplayName("New posts (/posts) tests")
public class CreatePostsTest {
    private final static Logger LOGGER = Logger.getLogger(CreatePostsTest.class.getSimpleName());

    public static void validateNewPostsResponse(Response response) {
        LOGGER.info("** Verifying new posts response **");
        LOGGER.info("Status code:" + response.statusCode());
        LOGGER.info("Status line:" + response.statusLine());
        LOGGER.info("Response:" + response.body().asString());

        assertThat(response.statusCode(), oneOf(201));

        // TODO: implement the additional verification for JSON schema stored at /resources/schemas
        //          see more at: https://www.baeldung.com/rest-assured-json-schema or http://wilddiary.com/validate-json-against-schema-in-java/
        //          to have something like validateNewPostsResponse(PostRequestBody postRequestBody, Response response)

        // TODO: implement the additional verification for the proper data returned in subsequent GET request accordingly to /models:
        //          new Gson().fromJson(jsonArray.toString(), PostResponseBody.class);
        //          new Gson().toJson(postRequestBody);

        LOGGER.info("** Verification: done");
    }

    // Happy path scenarios
    // TODO: Need to find right way of posting:
    //   - should we post form data, use URI encoding, or post JSON data (the current implementation)?
    //   - are any specific headers are required?
    //   - do post requests actually work?


    /**
     * prepares POST request from parameters
     * NEED WORK (see TODO above)
     *
     * @param params parameters
     * @return request spec
     */
    private RequestSpecification givenPost(Map<String, Object> params) {
        RequestSpecification reqSpec = given().contentType(ContentType.JSON);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            reqSpec.multiPart(entry.getKey(), entry.getValue());
        }
        return reqSpec;
    }


    // I had to "disable" POST tests, because we don't know if they are correct or not (for the lack of
    //   documentation). The Application under the test is not properly store the data.
    //   You can still run them with relevant maven profile.

    @Tag("broken")
    @DisplayName("POST /posts")
    @ParameterizedTest(name = "{index} => userId={0}, title={1}, body={2}")
    @MethodSource("newPostProvider")
    public void testPostNewPost(String userId, String title, String body) {
        validateNewPostsResponse(
                givenPost(Map.of())
                        .body(createNewPostJsonBody(userId, title, body))
                        .post(getBaseUrl() + "/posts")
        );
    }

    private static Stream<Arguments> newPostProvider() {
        return Stream.of(
                Arguments.of("1", "Title123", "Body for the Title123"),
                Arguments.of("2", "Title321", "Body for the Title321")
        );
    }

    /**
     * Helper method to make a proper JSON object required for the new Post creation
     * TODO: Move to the dedicated Utility or Helper class
     *
     * @param userId - the User ID
     * @param title  - the Title of the new Post
     * @param body   - the Body or the new Post Content
     * @return String Object
     */
    private static String createNewPostJsonBody(String userId, String title, String body) {
        PostRequestBody postRequestBody = new PostRequestBody();
        postRequestBody.setUserId(userId);
        postRequestBody.setTitle(title);
        postRequestBody.setBody(body);

        String jsonBody = new Gson().toJson(postRequestBody);
        LOGGER.info("JSON Body:" + jsonBody);

        return jsonBody;
    }
}
