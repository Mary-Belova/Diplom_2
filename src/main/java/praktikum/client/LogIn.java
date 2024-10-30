package praktikum.client;

public class LogIn {
    private String email;
    private String password;

    public LogIn() {

    }

    public LogIn(String email, String password) {
        this.email = email;
        this.password = password;
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

    //Метод для авторизации пользователя, позволяет получить данные рандомного курьера для тела запроса
    public static LogIn from(ClientDatabase client) {
        return new LogIn(client.getEmail(), client.getPassword());
    }
}