package praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static praktikum.Constant.*;

public class StatusCodeAndBodySteps {

    @Step("Проверка статуса и кода ответа, 200 Ok. Тело ответа содержит: success true; информация о пользователе.")
    public static void responseStatusAndBodyValidData(Response response) {
        response.then().statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue())
                .and().assertThat().body("refreshToken", notNullValue());
    }

    @Step ("Проверка статуса и кода ответа, 403 Forbidden. Тело ответа содержит: false, User already exists.")
    public static void responseStatusAndBodyCreateIndenticalAccount(Response response) {
        response.then().statusCode(FORBIDDEN_403)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("User already exists"));
    }
    @Step ("Проверка статуса и кода ответа, 403 Forbidden. Тело ответа содержит: false, Email, password and name are required fields.")
    public static void responseStatusBodyCreatedNullData(Response response) {
        response.then().statusCode(FORBIDDEN_403)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Step ("Проверка статуса и кода ответа, 401 UNAUTHORIZED. Тело ответа содержит: false, email or password are incorrect.")
    public static void responseStatusBodyLogInInvalidData(Response responseLogIn) {
        responseLogIn.then().statusCode(UNAUTHORISED_401)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
    @Step ("Проверка статуса и кода ответа, 200 Ok. Тело ответа содержит: success true; информацию о пользователе")
    public static void responseStatusAndBodyValidUpdateData(Response responseUpdateData) {
        responseUpdateData.then().log().all()
                .statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue());
    }
    @Step ("Проверка статуса и кода ответа, 403. Тело ответа содержит: success false; User with such email already exists.")
    public static void statusCodeBodyUserAlreadyExist(Response responseUpdateData) {
        responseUpdateData.then().statusCode(FORBIDDEN_403)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("User with such email already exists"));
    }
    @Step ("Проверка статуса и кода ответа, 401 Unauthorised. Тело ответа содержит: success false; You should be authorised")
    public static void statusCodeBodyUnauthorized(Response responseUpdateData) {
        responseUpdateData.then().statusCode(UNAUTHORISED_401)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }

}