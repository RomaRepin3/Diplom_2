import com.github.javafaker.Faker;
import dto.order.CreateOrderRequestDto;
import dto.register.success.RegisterRequestDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static requests.GetUserOrders.checkBodyAfterPositiveGetUserOrders;
import static requests.IngredientsApiMethods.getListOfIngredientsId;
import static requests.IngredientsApiMethods.getRandomIngredients;
import static requests.RegisterApiMethods.getDtoFromResponseRegisterUser;
import static requests.RestAssuredBaseMethods.*;
import static settings.Settings.*;

public class GetUserOrdersWithAuthorizationTest {

    private static final Faker faker = new Faker();

    private String loginAccessToken;
    private List<String> testRandomIngredients;

    @Before
    public void setUp(){
        // Генерация тестовых данных, создание пользователя, получение токена пользователя, получение ингридиентов
        // создание заказа

        RestAssured.baseURI = BASE_URL;

        Response responseWithRegisterUser = sendByPost(
                REGISTER_USER_URL,
                new RegisterRequestDto(
                        faker.internet().emailAddress(),
                        faker.internet().password(),
                        faker.name().firstName()
                )
        );

        loginAccessToken = getDtoFromResponseRegisterUser(responseWithRegisterUser).getAccessToken();

        testRandomIngredients = getRandomIngredients(getListOfIngredientsId(), 2);

        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                testRandomIngredients
        );
        sendByPostWithToken(CREATE_ORDER_URL, dtoJsonWithOrderIngredients, loginAccessToken);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя (авторизованный пользователь)")
    public void negativeGetUserOrdersWithoutAuthorization(){
        Response responseWithUserOrders = sendByGetWithToken(GET_USER_ORDERS_URL, loginAccessToken);

        checkResponseStatusCode(responseWithUserOrders, SUCCESS_STATUS_CODE);
        checkBodyAfterPositiveGetUserOrders(responseWithUserOrders, testRandomIngredients);
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(loginAccessToken);
    }
}
