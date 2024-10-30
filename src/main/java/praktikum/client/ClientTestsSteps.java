package praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import io.qameta.allure.Param;
import static io.qameta.allure.model.Parameter.Mode.HIDDEN;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static praktikum.Constant.*;

public class ClientTestsSteps {
    public static String tokenForDel;

    @Step("Удаление учетной записи пользователя")
    public void deleteUser() {
        given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .when()
                .delete(DELETE_ACCOUNT)
                .then().log().all().assertThat()
                .statusCode(ACCEPT_202);
        System.out.println("Учетная запись клиента успешно удалена");
    }


    @Step("Регистрация новой учетной записи пользователя, POST запрос")
    public static Response createNewClient(ClientDatabase client) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(client)
                .when()
                .post(CREATE_ACCOUNT);
        return response;
    }


    //Добавлена анотация hidden к аргументу token - в отчете allure скрыт токен пользовтеля
    @Step("Получение токена учетной записи пользователя")
    public static String getTokenForDel(@Param(mode=HIDDEN)String token) {
        return StringUtils.substringAfter(token, " ");
    }


    public static String getToken(Response response) {
        String token = response.then().extract().path("accessToken");
        return token;
    }


    @Step("Вход в существующую учетную запись пользователя")
    public static Response logInAccount(ClientDatabase client) {
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(LogIn.from(client))
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }

    @Step("Вход в учетную запись с невалидным значением ключа Email")
    public static Response logInInvalidEmail(ClientDatabase client) {
        String json = "{\"login\": \"abra@pochta.ru\"," + "\"password\": \"" + client.getPassword() + "\" " + "}";
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }


    @Step("Вход в учетную запись с невалидным значением ключа Password")
    public static Response logInInvalidPassword(ClientDatabase client) {
        String json = "{\"login\": \"" + client.getEmail() + "\", \"password\": \"111111\"" + "}";
        Response responseLogIn = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(LOG_IN_ACCOUNT);
        return responseLogIn;
    }

    @Step("Patch запрос с токеном на изменение электронной почты пользователя")
    public Response updateClientEmail() {
        String emailUpdate = "updEmail@pochta.ru";
        String json = "{\"login\": \"" + emailUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос с токеном на изменение пароля учетной записи пользователя")
    public Response updateClientPassword() {
        String passwordUpdate = "111456";
        String json = "{\"password\": \"" + passwordUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос с токеном на изменение имени пользователя в его учетной записи")
    public Response updateClientName() {
        String nameUpdate = "Mary";
        String json = "{\"name\": \"" + nameUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос с токеном на изменение электронного адреса пользователя и передача в теле запроса уже используемого адреса")
    public Response responseUpdateIdenticalEmail(ClientDatabase client) {
        String json = "{\"email\": \"" + client.getEmail() + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос без токена на изменение электронной почты пользователя")
    public static Response responseUpdateEmailUnauthorized() {
        String emailUpdate = "updEmail@pochta.ru";
        String json = "{\"login\": \"" + emailUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос без токена на изменение пароля пользователя")
    public static Response responseUpdatePasswordUnauthorized() {
        String passwordUpdate = "111456";
        String json = "{\"password\": \"" + passwordUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }

    @Step("Patch запрос без токена на изменение имени пользователя учетной записи")
    public static Response responseUpdateNameUnauthorized() {
        String nameUpdate = "Mary";
        String json = "{\"name\": \"" + nameUpdate + "\"}";
        Response responseUpdateData = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .patch(UPDATE_USER_DATA);
        return responseUpdateData;
    }
}