import io.qameta.allure.junit4.DisplayName;
import praktikum.client.ClientDatabase;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.ClientTestsSteps;
import static io.restassured.RestAssured.baseURI;
import static praktikum.client.StatusCodeAndBodySteps.*;


public class ClientTest extends ClientTestsSteps {

    @Before
    public void setUp() {
        baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    @DisplayName("Удаление учетной записи пользователя")
    public void deleteAccount() {
        if (tokenForDel != null) {
            deleteUser();
        }
    }


    @Test
    @DisplayName("Успешное создание аккаунта пользователя")
    public void successCreatedAccount() {
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @Test
    @DisplayName("Получение ошибки при создании пользователя, ранее зарегистрированного в системе")
    public void errorCreateIdenticalAccount() {
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        Response doubleResponse = createNewClient(client);
        responseStatusAndBodyCreateIndenticalAccount(doubleResponse);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);
    }

    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе email")
    public void errorCreatedAccountWithNullEmail() {
        ClientDatabase client = ClientDatabase.newClientWithNullEmail();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }
    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе password")
    public void errorCreatedAccountWithNullPassword() {
        ClientDatabase client = ClientDatabase.newClientWithNullPassword();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }

    @Test
    @DisplayName("Получение ошибки регистрации пользователя со значением null в ключе name")
    public void errorCreatedAccountWithNullName() {
        ClientDatabase client = ClientDatabase.newClientWithNullName();
        Response response = createNewClient(client);

        responseStatusBodyCreatedNullData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

    }

}