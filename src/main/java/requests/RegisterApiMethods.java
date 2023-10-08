package requests;

import dto.register.success.RegisterResponseAllDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;


/**
 * <H2>Проверки и получение ответов регистрации.</H2>
 */
public class RegisterApiMethods extends RestAssuredBaseMethods {

    @Step("Проверка тела ответа после регистрации пользователя")
    public static void checkBodyAfterSuccessfulRegister(Response response, String userEmail, String userName){
        checkResponseBodyJson(response, "success", true);
        checkResponseBodyJson(response, "user.email", userEmail);
        checkResponseBodyJson(response, "user.name", userName);
        checkResponseBodyJsonNotNullKey(response, "accessToken");
        checkResponseBodyJsonNotNullKey(response, "refreshToken");
    }

    @Step("Проверка тела ответа если пользователь уже существует")
    public static void checkBodyUserAlreadyExist(Response response){
        checkResponseBodyJson(response, "success", false);
        checkResponseBodyJson(response, "message", "User already exists");
    }

    @Step("Проверка тела ответа если пользователь уже существует")
    public static void checkBodyUserRequiredField(Response response){
        checkResponseBodyJson(response, "success", false);
        checkResponseBodyJson(response, "message", "Email, password and name are required fields");
    }


    @Step("Получение ответа из тела после регистрации пользователя (в виде POJO)")
    public static RegisterResponseAllDto getPojoFromResponsePositiveRegisterUser(Response response){
        return response.as(RegisterResponseAllDto.class);
    }

}
