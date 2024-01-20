package com.qacart.todo.testCases;

import com.qacart.todo.apis.TodoAPIs;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
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
        Todo todo = TodoSteps.generateTodo();
        String token = UserSteps.getToken();
        Response response = TodoAPIs.addTodo(todo, token);

        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }
    @Test
    public void shouldFailToAddTaskIfIsCompletedIsMissing(){
        Todo todo = new Todo("damn");
        String token = UserSteps.getToken();
        Response response = TodoAPIs.addTodo(todo, token);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_REQUIRED));
    }
//    @Test
//    public void ShouldReturnTasks()
//    {
//        String token = UserSteps.getToken();
//        Response response = given()
//                .baseUri("https://qacart-todo.herokuapp.com/api/v1")
//                .auth().oauth2(token)
//        .when()
//                .get("tasks")
//        .then()
//                .log().all()
//                .extract().response();
//
//        Todo returnedTodo = response.body().as(Todo.class);
//        assertThat(response.statusCode(), equalTo(200));
//    }
    @Test
    public void shouldSucceedToGetTodoByID(){
        Todo todo = TodoSteps.generateTodo();
        String token = UserSteps.getToken();
        String taskId = TodoSteps.getTodoId(todo, token);
        Response response = TodoAPIs.getTodo(taskId, token);
        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }

    @Test
    public void shouldDeleteTodo(){
        Todo todo = TodoSteps.generateTodo();
        String token = UserSteps.getToken();
        String taskId = TodoSteps.getTodoId(todo, token);

        Response response = TodoAPIs.deleteTodo(taskId, token);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }
}
