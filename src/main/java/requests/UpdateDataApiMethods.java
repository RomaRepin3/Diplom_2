package requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;


/**
 * <H2>Проверки ответов при обновлении данных.</H2>
 */
public class UpdateDataApiMethods extends RestAssuredBaseMethods {

    @Step("Проверка тела ответа после позитивного обновления данных пользователя")
    public static void checkSuccessfulUpdatedUsersData(Response response, String userEmail, String userName){
        checkResponseBodyJson(response, "success", true);
        checkResponseBodyJson(response, "user.email", userEmail);
        checkResponseBodyJson(response, "user.name", userName);
    }

    @Step("Проверка тела ответа после негативного обновления данных пользователя")
    public static void checkBodyAfterUnsuccessfulUpdatedUser(Response response){
        checkResponseBodyJson(response, "success", false);
        checkResponseBodyJson(response, "message", "You should be authorised");
    }

}
