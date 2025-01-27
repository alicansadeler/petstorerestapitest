package org.alicansadeler.constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
public class UserEndPoints {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER_ENDPOINT = "/user";
    private static final String CREATE_ENDPOINT = "/createWithList";

    public static final String CREATE_USER = USER_ENDPOINT;
    public static final String GET_USER = USER_ENDPOINT + "/{username}";
    public static final String UPDATE_USER = USER_ENDPOINT + "/{username}";
    public static final String DELETE_USER = USER_ENDPOINT + "/{username}";
    public static final String CREATE_USERS_WITH_LIST = USER_ENDPOINT + "/createWithList";
    public static final String USER_LOGIN = USER_ENDPOINT + "/login";
    public static final String USER_LOGOUT = USER_ENDPOINT + "/logout";

    public static RequestSpecification userSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
