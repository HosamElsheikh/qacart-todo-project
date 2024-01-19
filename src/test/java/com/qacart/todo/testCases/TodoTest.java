package com.qacart.todo.testCases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TodoTest {
//    String body = "{\n" +
//            "    \"isCompleted\": false\n" +
//            "}";
    @Test
    public void shouldAddTask(){
        Todo todo = new Todo(false, "Noice");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNTQ5OTI1Mn0.NSWCcERDfowcZny0CX1VA99KPOTdvRCaW815Upt-juU";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
        .when()
                .post("tasks")
        .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("item"), equalTo(todo.getItem()));
//                .assertThat().statusCode(201)
//                .assertThat().body("item", equalTo(todo.getItem()));
    }
    @Test
    public void shouldFailToAddTaskIfIsCompletedIsMissing(){
        Todo todo = new Todo("Noice");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNTQ5OTI1Mn0.NSWCcERDfowcZny0CX1VA99KPOTdvRCaW815Upt-juU";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("tasks")
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("\"isCompleted\" is required"));

//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("\"isCompleted\" is required"));
    }
    @Test
    public void ShouldReturnTasks()
    {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNTQ5OTI1Mn0.NSWCcERDfowcZny0CX1VA99KPOTdvRCaW815Upt-juU";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .auth().oauth2(token)
        .when()
                .get("tasks")
        .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
//                .assertThat().statusCode(200)
//                .assertThat().body("tasks.[0]", hasKey("__v"));
    }
    @Test
    public void shouldSucceedToGetTodoByID(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNDk5MTQ0N30.GJPvokDMXCOBu2K26AQgf-bwtjWiYPpD5FG0Ugr6F44";
        String taskId = "65a01aef2e2f680014c65813";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
        .when()
                .get("tasks/" + taskId)
        .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("item"), equalTo("Learn Appium"));
        assertThat(response.path("isCompleted"), equalTo(false));
//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));
    }

    @Test
    public void shouldDeleteTodo(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1YTAxNTk1MmUyZjY4MDAxNGM2NTdlNiIsImZpcnN0TmFtZSI6Ikhvc2FtIiwibGFzdE5hbWUiOiJFbHNoZWlraCIsImlhdCI6MTcwNDk5MTQ0N30.GJPvokDMXCOBu2K26AQgf-bwtjWiYPpD5FG0Ugr6F44";
        String taskId = "65a01aef2e2f680014c65813";
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
        .when()
                .delete("tasks/" + taskId)
                .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));
    }
}
