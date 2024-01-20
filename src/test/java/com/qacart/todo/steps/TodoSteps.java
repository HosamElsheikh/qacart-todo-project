package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoAPIs;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class TodoSteps {
    public static Todo generateTodo(){
        Faker faker = new Faker();
        String item = faker.educator().course();
        boolean isCompleted = false;
        return new Todo(isCompleted, item);
    }

    public static String getTodoId(Todo todo, String token){
        Response response = TodoAPIs.addTodo(todo, token);
        return response.body().path("_id");
    }
}
