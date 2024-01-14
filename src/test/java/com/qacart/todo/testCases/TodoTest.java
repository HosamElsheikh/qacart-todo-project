package com.qacart.todo.testCases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {
    String body = "{\n" +
            "    \"isCompleted\": false,\n" +
            "    \"item\": \"Learn Appium\"\n" +
            "}";
    @Test
    public void shouldAddTask(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNDk5MTQ0N30.GJPvokDMXCOBu2K26AQgf-bwtjWiYPpD5FG0Ugr6F44";
        given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .body(body)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("tasks")
        .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("item", equalTo("Learn Appium"))
                .assertThat().body("isCompleted", equalTo("false"));
    }
}
