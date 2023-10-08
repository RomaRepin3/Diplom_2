package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;


/**
 * <H2>Проверки ответов при получении заказов.</H2>
 */
public class GetUserOrders extends RestAssuredBaseMethods {

    @Step("Проверка тела ответа после получения заказов у неавторизованного пользователя")
    public static void checkBodyAfterNegativeGetUserOrdersWithoutAuthorization(Response response){
        checkResponseBodyJson(response, "success", false);
        checkResponseBodyJson(response, "message", "You should be authorised");
    }

    @Step("Проверка тела ответа после получения заказов у аторизованного пользователя")
    public static void checkBodyAfterPositiveGetUserOrders(Response response, List<String> ingredients){
        checkResponseBodyJson(response, "success", true);
        checkResponseBodyJson(response, "orders.ingredients", ingredients);
        checkResponseBodyJsonNotNullKey(response, "total");
        checkResponseBodyJsonNotNullKey(response, "totalToday");
    }


}
