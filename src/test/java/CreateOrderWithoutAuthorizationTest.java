import dto.order.CreateOrderRequestDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static requests.CreateOrderApiMethods.checkNegativeCreatedOrderWithoutAuthorization;
import static requests.IngredientsApiMethods.getListOfIngredientsId;
import static requests.IngredientsApiMethods.getRandomIngredients;
import static requests.RestAssuredBaseMethods.checkResponseStatusCode;
import static requests.RestAssuredBaseMethods.sendByPost;
import static settings.Settings.*;

public class CreateOrderWithoutAuthorizationTest {

    private List<String> listOfAllIngredientsId;

    @Before
    public void setUp() {
        // Получение ингридиентов
        RestAssured.baseURI = BASE_URL;

        listOfAllIngredientsId = getListOfIngredientsId();
    }

    @Test
    @DisplayName("Создание заказа (без авторизации)")
    public void negativeCreateOrderWithoutAuthorization(){
        CreateOrderRequestDto dtoJsonWithOrderIngredients = new CreateOrderRequestDto(
                getRandomIngredients(listOfAllIngredientsId, 5)
        );

        Response responseWithOrderCreated = sendByPost(CREATE_ORDER_URL, dtoJsonWithOrderIngredients);
        checkResponseStatusCode(responseWithOrderCreated, SUCCESS_STATUS_CODE);
        checkNegativeCreatedOrderWithoutAuthorization(responseWithOrderCreated);
    }

}
