package com.qacart.todo.testCases;

import com.qacart.todo.apis.UserAPIs;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {
    @Test
    public void shouldRegister(){
        User user = UserSteps.generateUser();

        Response response = UserAPIs.register(user);
        User returnedUser = response.body().as(User.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Test
    public void shouldFailToRegisterTheSameEmail(){

        User user = UserSteps.getRegisterUser();

        Response response = UserAPIs.register(user);

        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.SAME_EMAIL));
//        assertThat(response.path("message"), equalTo("Email is already exists in the Database"));
//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shoudLogin() {
        User user = UserSteps.getRegisterUser();
        User loginData = new User(user.getEmail(), user.getPassword());
        Response response = UserAPIs.login(loginData);

        User returnedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccess_token(), not(equalTo(null)));
    }

    @Test
    public void shouldFailToLoginWithIncorrectPassword(){

        User user = UserSteps.getRegisterUser();
        User loginData = new User(user.getEmail(), "wrongPassword");

        Response response = UserAPIs.login(loginData);

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.WRONG_CREDENTIALS));
    }
}
