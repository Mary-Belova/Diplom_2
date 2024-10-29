import io.qameta.allure.junit4.DisplayName;
import praktikum.client.ClientDatabase;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.client.ClientTestsSteps;
import static io.restassured.RestAssured.baseURI;
import static praktikum.client.StatusCodeAndBodySteps.responseStatusAndBodyValidData;
import static praktikum.client.StatusCodeAndBodySteps.responseStatusBodyLogInInvalidData;

public class LogInTest extends ClientTestsSteps  {


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
    @DisplayName("Успешная авторизация пользователя")
    public void successLogInAccount() {
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseLogIn = logInAccount(client);
        responseStatusAndBodyValidData(responseLogIn);


    }

    @Test
    @DisplayName("Ошибка при авторизации с невалидным адресом почтового ящика")
    public void errorLogInInvalidEmail() {
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseLogIn = logInInvalidEmail(client);
        responseStatusBodyLogInInvalidData(responseLogIn);
    }

    @Test
    @DisplayName("Ошибка при авторизации с невалидным паролем")
    public void errorLogInInvalidPassword() {
        ClientDatabase client = ClientDatabase.newRandomClient();
        Response response = createNewClient(client);
        responseStatusAndBodyValidData(response);

        String token = getToken(response);
        tokenForDel = getTokenForDel(token);

        Response responseLogIn = logInInvalidPassword(client);
        responseStatusBodyLogInInvalidData(responseLogIn);
    }

}