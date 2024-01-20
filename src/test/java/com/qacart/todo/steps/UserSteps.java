package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserAPIs;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser(){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "ilikeQAcart";

        User user = new User(firstName, lastName, email, password);
        return user;
    }

    public static User getRegisterUser(){
        User user = generateUser();
        UserAPIs.register(user); //Now the user is registered
        return user;
    }
    public static String getToken(){
        User user = generateUser();
        Response response = UserAPIs.register(user);
        return response.body().path("access_token");
    }



}
