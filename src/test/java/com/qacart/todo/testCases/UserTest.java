package com.qacart.todo.testCases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {
//    String body = "{\n" +
//            "    \"firstName\": \"Hosam\",\n" +
//            "    \"lastName\": \"Elsheikh\",\n" +
//            "    \"email\": \"HosamElsheikh32@gmail.com\",\n" +
//            "    \"password\": \"12345678\"\n" +
//            "}";
    @Test
    public void shouldRegister(){
        User user = new User("Hosam", "Elsheikh", "tesftt@efmail.com", "123456789");
//        user.setFirstName("Hosam");
//        user.setLastName("Elsheikh");
//        user.setEmail("wow@xd.com");
//        user.setPassword("1234567890");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.path("firstName"), equalTo(user.getFirstName()));
//                .assertThat().statusCode(201)
//                .assertThat().body("firstName", equalTo("Hosam"));
    }

    @Test
    public void shouldFailToRegisterTheSameEmail(){
//        String body = "{\n" +
//                "    \"firstName\": \"Hosam\",\n" +
//                "    \"lastName\": \"Elsheikh\",\n" +
//                "    \"email\": \"HosamElsheikh32@gmail.com\",\n" +
//                "    \"password\": \"12345678\"\n" +
//                "}";
        User user = new User("Hosam", "Elsheikh", "test@email.com", "123456789");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.path("message"), equalTo("Email is already exists in the Database"));
//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shoudLogin(){
//        String body = "{\n" +
//                "    \"email\": \"HosamElsheikh@gmail.com\",\n" +
//                "    \"password\": \"12345678\"\n" +
//                "}";
        User user = new User("test@email.com", "123456789");
        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/login")
        .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.path("firstName"), equalTo("Hosam"));
        assertThat(response.path("access_token"), not(equalTo(null)));
//                .assertThat().statusCode(200)
//                .assertThat().body("firstName", equalTo("Hosam"))
//                .assertThat().body("access_token", not(equalTo(null)));
    }

    @Test
    public void shouldFailToLoginWithIncorrectPassword(){
//        String body = "{\n" +
//                "    \"email\": \"HosamElsheikh@gmail.com\",\n" +
//                "    \"password\": \"123456781\"\n" +
//                "}";
        User user = new User("test@email.com", "1234567893");

        Response response = given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/login")
        .then()
                .log().all()
                .extract().response();
        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.path("message"), equalTo("The email and password combination is not correct, please fill a correct email and password"));
//                .assertThat().statusCode(401)
//                .assertThat().body("message", equalTo("The email and password combination is not correct, please fill a correct email and password"));
    }
}
