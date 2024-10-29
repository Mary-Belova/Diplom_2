package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.client.ClientTestsSteps;
import java.util.List;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static praktikum.Constant.*;

public class OrderList extends ClientTestsSteps {


    @Step("Get запрос на получение хэша ингредиента")
    public String getIngredientHash() {
        List<String> ingredients = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .auth().oauth2(tokenForDel)
                .when()
                .get(GET_INGREDIENTS)
                .then().extract().path("data._id");
        String ingredientForORDER = ingredients.get(1);
        System.out.println(ingredientForORDER);
        return ingredientForORDER;
    }


    @Step("Post запрос на создание заказа. Переданы: токен пользователя, хэш код ингредиента")
    public static Response createOrderWithAuthAndIngredientHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        return response;
    }

    @Step ("Проверка статуса и кода ответа, 200 Ok. Тело ответа содержит: success true; информацию о заказе")
    public static void responseStatusAndBodyOrderWithHasgAndAuth(Response response) {
        response.then().statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("order", notNullValue())
                .and().assertThat().body("name", notNullValue());
    }

    @Step("Post запрос на создание заказа, токен не передан. В теле запроса передан хэш код ингредиента")
    public static Response createOrderWithoutAuthAndIngredientHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        return response;
    }

    @Step ("Проверка статуса и кода ответа, 301 Redirect.")
    public static void responseStatusAndBodyOrderWithoutAuth(Response response) {
        response.then().statusCode(REDIRECT_301);
    }

    @Step ("Проверка статуса и кода ответа, 400 Bad request.")
    public static void responseStatusAndBodyOrderWithoutHash(Response response) {
        response.then().statusCode(BAD_REQUEST_400)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }


    @Step("Получение невалидного хеша ингридиента")
    public static String responseOrderWithInvalidHash() {
        List<String> ingredients = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .auth().oauth2(tokenForDel)
                .when()
                .get(GET_INGREDIENTS)
                .then().extract().path("data._id");
        String ingredientForORDER = ingredients.get(1);
        String invalidHash = ingredientForORDER.replace("a", "h");
        return invalidHash;
    }

    @Step ("Проверка статуса и кода ответа, 500 Internal server error.")
    public static void responseStatusBodyInvalidHash(String json) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .body(json)
                .when()
                .post(CREATE_ORDER_POST);
        response.then().statusCode(INTERNAL_SERVER_ERROR_500);
    }

    @Step ("Get запрос на получение списка заказов авторизованного клиента")
    public static Response getUserOrderListWithAuthorization() {
        Response responseGetOrder = given().log().all()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenForDel)
                .baseUri(baseURI)
                .when()
                .get(GET_USER_ORDERS);
        return responseGetOrder;
    }

    @Step ("Проверка статуса и кода ответа 200 OK, Тело ответа содержит: success true; информацию о заказе .")
    public static void responseStatusBodyOrderListWithAuth(Response responseGetOrder) {
        responseGetOrder.then().statusCode(OK_200)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("orders.ingredients", notNullValue());
    }

    @Step ("Get запрос на получение списка заказов неавторизованного клиента")
    public static Response getUserOrderListWithoutAuthorization() {
        Response responseGetOrder = given().log().all()
                .header("Content-type", "application/json")
                .baseUri(baseURI)
                .when()
                .get(GET_USER_ORDERS);
        return responseGetOrder;
    }

    @Step ("Проверка статуса и кода ответа 401 Unauthorised. Тело ответа содержит: success false; message: You should be authorised .")
    public static void responseStatusBodyOrderListWithoutAuth(Response responseGetOrder) {
        responseGetOrder.then().statusCode(UNAUTHORISED_401)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }
}