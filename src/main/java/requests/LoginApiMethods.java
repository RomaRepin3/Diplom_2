package requests;

import dto.login.success.LoginResponseAllDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;


/**
 * <H2>Проверки ответов при авторизации.</H2>
 */
public class LoginApiMethods extends RestAssuredBaseMethods {

    @Step("Проверка тела ответа после позитивной авторизации пользователя")
    public static void checkBodyAfterSuccessfulLogin(Response response, String userEmail, String userName){
        checkResponseBodyJson(response, "success", true);
        checkResponseBodyJsonNotNullKey(response, "accessToken");
        checkResponseBodyJsonNotNullKey(response, "refreshToken");
        checkResponseBodyJson(response, "user.email", userEmail);
        checkResponseBodyJson(response, "user.name", userName);
    }

    @Step("Проверка тела ответа после негативной авторизации пользователя")
    public static void checkBodyAfterNegativeLogin(Response response){
        checkResponseBodyJson(response, "success", false);
        checkResponseBodyJson(response, "message", "email or password are incorrect");
    }

    @Step("Полчучение ответа из тела после авторизации пользователя (в виде POJO)")
    public static LoginResponseAllDto getPojoFromResponsePositiveLoginUser(Response response){
        return response.as(LoginResponseAllDto.class);
    }

}
