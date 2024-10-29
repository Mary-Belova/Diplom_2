import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.ClientDatabase;
import praktikum.client.ClientTestsSteps;
import praktikum.order.OrderList;
import static io.restassured.RestAssured.baseURI;
import static praktikum.client.StatusCodeAndBodySteps.responseStatusAndBodyValidData;
import static praktikum.order.OrderList.*;


public class CreateOrderTest extends ClientTestsSteps {
    OrderList order = new OrderList();

    @Before
    @DisplayName("Регистрация учетной записи пользователя")
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
        // Создание клиента
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        //Получение токена клиента
        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            deleteUser();
        }
    }

    @Test
    @DisplayName("Успешный запрос на создание заказа, передан токен пользователя, в теле запроса передан валидный хэш ингредиента")
    public void createOrderWithAuthorizationAndHash () {

        String json = "{\"ingredients\": [\"" + order.getIngredientHash() + "\"" + "]}";
        Response response = OrderList.createOrderWithAuthAndIngredientHash(json);
        responseStatusAndBodyOrderWithHasgAndAuth(response);

    }

    @Test
    @DisplayName("Проверка получения ошибки 301 Redirect при запросе на создание заказа без токена пользователя, в теле запроса передан валидный хэш ингредиента")
    public void createOrderWithoutAuthorization () {

        String json = "{\"ingredients\": [\"" + order.getIngredientHash() + "\"" + "]}";
        Response response = createOrderWithoutAuthAndIngredientHash(json);
        responseStatusAndBodyOrderWithoutAuth(response);

    }

    @Test
    @DisplayName("Проверка получения ошибки 400 Bad Request при запросе создания заказа без хеша ингредиента")
    public void createOrderWithoutHash () {

        String json = "{\"ingredients\": []}";
        Response response = OrderList.createOrderWithAuthAndIngredientHash(json);
        responseStatusAndBodyOrderWithoutHash(response);

    }
    @Test
    @DisplayName("Проверка получения ошибки 500 Internal server error при запросе с невалидным хешом ингридиента")
    public void createOrderWithInvalidHash () {
        //Получение невалидного хэша ингридиента
        String invalidHash = responseOrderWithInvalidHash();

        String json = "{\"ingredients\": [\"" + invalidHash + "\"" + "]}";
        responseStatusBodyInvalidHash(json);

    }
}