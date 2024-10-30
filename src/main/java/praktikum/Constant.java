package praktikum;

public class Constant {
    //Константы кодов ответа от бэкенда
    public static int OK_200 = 200;
    public static int ACCEPT_202 = 202;
    public static int BAD_REQUEST_400 = 400;
    public static int FORBIDDEN_403 = 403;
    public static int UNAUTHORISED_401 = 401;
    public static int REDIRECT_301 = 301;
    public static int INTERNAL_SERVER_ERROR_500 = 500;



    //Константы ручек

    //Клиент
    public static String CREATE_ACCOUNT = "/api/auth/register"; //POST - регистрация клиента
    public static String DELETE_ACCOUNT = "/api/auth/user"; //DELETE - удаление клиента
    public static String LOG_IN_ACCOUNT = "/api/auth/login"; //POST - авторизация
    public static String UPDATE_USER_DATA = "/api/auth/user"; //PATCH - обновление данных пользователя

    //Заказ
    public static String CREATE_ORDER_POST = "/api/orders"; //POST - создание заказа
    public static String GET_USER_ORDERS = "/api/orders";//GET - получение списка заказов пользователя
    public static String GET_INGREDIENTS = "/api/ingredients";
}