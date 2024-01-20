package com.qacart.todo.apis;

import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoAPIs {
    public static Response addTodo(Todo todo, String token){
        return given()
                .baseUri(Route.v1Uri)
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post(Route.TODOS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getTodo(String taskId,String token){
        return given()
                .baseUri(Route.v1Uri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS_ROUTE + "/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response deleteTodo(String taskId, String token){
        return given()
                .baseUri(Route.v1Uri)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOS_ROUTE + "/" + taskId)
                .then()
                .log().all()
                .extract().response();
    }
}
