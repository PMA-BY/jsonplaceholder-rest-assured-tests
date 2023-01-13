package models;

/**
 * ## POJO of POST /posts
 * <p>
 * These POJO is used on `POST /posts` endpoint for the validation of the response of the new `Post` JSON Object creation
 */

public class PostResponseBody {
    private String userId;
    private int id;
    private String title;
    private String body;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
