package com.qacart.todo.testCases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest {
    String body = "{\n" +
            "    \"firstName\": \"Hosam\",\n" +
            "    \"lastName\": \"Elsheikh\",\n" +
            "    \"email\": \"HosamElsheikh32@gmail.com\",\n" +
            "    \"password\": \"12345678\"\n" +
            "}";
    @Test
    public void shouldRegister(){
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName", equalTo("Hosam"));
    }

    @Test
    public void shouldFailToRegisterTheSameEmail(){
        String body = "{\n" +
                "    \"firstName\": \"Hosam\",\n" +
                "    \"lastName\": \"Elsheikh\",\n" +
                "    \"email\": \"HosamElsheikh32@gmail.com\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shoudLogin(){
        String body = "{\n" +
                "    \"email\": \"HosamElsheikh@gmail.com\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("/api/v1/users/login")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("firstName", equalTo("Hosam"))
                .assertThat().body("access_token", not(equalTo(null)));
    }

    @Test
    public void shouldFailToLoginWithIncorrectPassword(){
        String body = "{\n" +
                "    \"email\": \"HosamElsheikh@gmail.com\",\n" +
                "    \"password\": \"123456781\"\n" +
                "}";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("/api/v1/users/login")
        .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}
