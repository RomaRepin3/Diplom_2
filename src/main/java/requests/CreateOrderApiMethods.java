package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;


/**
 * <H2>Проверки тела ответа при создании заказа.</H2>
 */
public class CreateOrderApiMethods extends RestAssuredBaseMethods {

    @Step("Проверка тела ответа после успешного создания заказа")
    public static void checkSuccessfulCreatedOrder(Response response, String userEmail, String userName){
        checkResponseBodyJson(response,"success", true);
        checkResponseBodyJsonNotNullKey(response,"name");
        checkResponseBodyJsonNotNullKey(response,"order.ingredients");
        checkResponseBodyJsonNotNullKey(response,"order._id");
        checkResponseBodyJson(response,"order.owner.name", userName);
        checkResponseBodyJson(response,"order.owner.email", userEmail);
        checkResponseBodyJsonNotNullKey(response,"order.owner.createdAt");
        checkResponseBodyJsonNotNullKey(response,"order.owner.updatedAt");
        checkResponseBodyJsonNotNullKey(response,"order.status");
        checkResponseBodyJsonNotNullKey(response,"order.name");
        checkResponseBodyJsonNotNullKey(response,"order.createdAt");
        checkResponseBodyJsonNotNullKey(response,"order.updatedAt");
        checkResponseBodyJsonNotNullKey(response,"order.number");
        checkResponseBodyJsonNotNullKey(response,"order.price");
    }

    @Step("Проверка тела ответа после негативного создания заказа (без ингредиентов в заказе)")
    public static void checkNegativeCreatedOrderWithoutIngredients(Response response){
        checkResponseBodyJson(response,"success", false);
        checkResponseBodyJson(response,"message", "Ingredient ids must be provided");
    }

    @Step("Проверка тела ответа после негативного создания заказа (без авторизации)")
    public static void checkNegativeCreatedOrderWithoutAuthorization(Response response){
        checkResponseBodyJson(response,"success", true);
        checkResponseBodyJsonNotNullKey(response,"name");
        checkResponseBodyJsonNotNullKey(response,"order.number");
    }

}
