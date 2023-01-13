package models;


/**
 * ## POJO of POST /posts
 * <p>
 * These POJO is used on `POST /posts` endpoint for the new `Post` JSON Object creation
 */

public class PostRequestBody {
    private String userId;
    private String title;
    private String body;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
