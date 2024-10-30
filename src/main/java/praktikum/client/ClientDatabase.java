package praktikum.client;

import org.apache.commons.lang3.RandomStringUtils;

public class ClientDatabase {
    private String email;
    private String password;
    private String name;


    public ClientDatabase(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public ClientDatabase() {
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    //Метод с данными рандомного пользователя, все обязательные значения заполненны
    public static ClientDatabase newRandomClient() {
        return new ClientDatabase("Belova_10@yandex.ru" + RandomStringUtils.randomAlphabetic(5), "123456", "Katya");
    }

    //Метод со значением Null в ключе Email
    public static ClientDatabase newClientWithNullEmail() {
        return new ClientDatabase(null, "123456", "Katya");
    }

    //Метод со значением Null в ключе Password
    public static ClientDatabase newClientWithNullPassword() {
        return new ClientDatabase("Belova_10@yandex.ru" + RandomStringUtils.randomAlphabetic(5), null, "Katya");
    }

    //Метод со значением Null в ключе Name
    public static ClientDatabase newClientWithNullName() {
        return new ClientDatabase("Belova_10@yandex.ru" + RandomStringUtils.randomAlphabetic(5), "123456", null);
    }
}