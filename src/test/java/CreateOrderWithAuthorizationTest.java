import com.github.javafaker.Faker;
import dto.order.CreateOrderRequestDto;
import dto.register.success.RegisterRequestDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static requests.CreateOrderApiMethods.checkNegativeCreatedOrderWithoutIngredients;
import static requests.CreateOrderApiMethods.checkSuccessfulCreatedOrder;
import static requests.IngredientsApiMethods.*;
import static requests.RegisterApiMethods.getDtoFromResponseRegisterUser;
import static settings.Settings.*;

public class CreateOrderWithAuthorizationTest {

    private static final Faker faker = new Faker();

    private String generatedTestEmail;
    private String generatedTestName;

    private String loginAccessToken;
    private List<String> listOfAllIngredientsId;

    @Before
    public void setUp() {
        // Генерация тестовых данных, создание пользователя, получение токена пользователя, получение ингридиентов
        RestAssured.baseURI = BASE_URL;

        generatedTestEmail = faker.internet().emailAddress();
        generatedTestName = faker.name().firstName();

        Response responseWithRegisterUser = sendByPost(
                REGISTER_USER_URL,
                new RegisterRequestDto(
                        generatedTestEmail,
                        faker.internet().password(),
                        generatedTestName
                )
        );

        loginAccessToken = getDtoFromResponseRegisterUser(responseWithRegisterUser).getAccessToken();

        listOfAllIngredientsId = getListOfIngredientsId();
    }

    @Test
    @DisplayName("Создание заказа (с авторизацией)")
    public void createPositiveOrderWithAuthorization(){
        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                getRandomOneIngredient(listOfAllIngredientsId)
        );

        Response responseWithOrderCreated = sendByPostWithToken(CREATE_ORDER_URL, dtoJsonWithOrderIngredients, loginAccessToken);
        checkResponseStatusCode(responseWithOrderCreated, SUCCESS_STATUS_CODE);
        checkSuccessfulCreatedOrder(responseWithOrderCreated, generatedTestEmail, generatedTestName);
    }

    @Test
    @DisplayName("Создание заказа (с ингредиентами)")
    public void createPositiveOrderWithIngredients(){
        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                getRandomIngredients(listOfAllIngredientsId, 5)
        );

        Response responseWithOrderCreated = sendByPostWithToken(CREATE_ORDER_URL, dtoJsonWithOrderIngredients, loginAccessToken);
        checkResponseStatusCode(responseWithOrderCreated, SUCCESS_STATUS_CODE);
        checkSuccessfulCreatedOrder(responseWithOrderCreated, generatedTestEmail, generatedTestName);
    }

    @Test
    @DisplayName("Создание заказа (без ингредиентов)")
    public void negativeCreateOrderWithoutIngredients(){
        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                new ArrayList<>()
        );

        Response responseWithOrderCreated = sendByPostWithToken(CREATE_ORDER_URL, dtoJsonWithOrderIngredients, loginAccessToken);
        checkResponseStatusCode(responseWithOrderCreated, BAD_REQUEST_STATUS_CODE);
        checkNegativeCreatedOrderWithoutIngredients(responseWithOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа (с неверным хешем ингредиентов)")
    public void negativeCreateOrderWithWrongIngredients(){
        List<String> listIngredients= new ArrayList<>();
        listIngredients.add("Wrong_Ingredients");

        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                listIngredients
        );

        Response responseWithOrderCreated = sendByPostWithToken(CREATE_ORDER_URL, dtoJsonWithOrderIngredients, loginAccessToken);
        checkResponseStatusCode(responseWithOrderCreated, INTERNAL_SERVER_ERROR_STATUS_CODE);
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(loginAccessToken);
    }
}