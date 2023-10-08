import com.github.javafaker.Faker;
import dto.login.success.LoginRequestDto;
import dto.register.success.RegisterRequestDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static requests.LoginApiMethods.checkBodyAfterNegativeLogin;
import static requests.LoginApiMethods.checkBodyAfterSuccessfulLogin;
import static requests.RegisterApiMethods.getDtoFromResponseRegisterUser;
import static requests.RestAssuredBaseMethods.*;
import static settings.Settings.*;

public class LoginTest {

    private static final Faker faker = new Faker();

    private String generatedTestEmail;
    private String generatedTestPassword;
    private String generatedTestName;

    private Response responseWithCreatedUser;

    @Before
    public void setUp(){
        // Генерация тестовых данных, создание пользователя
        RestAssured.baseURI = BASE_URL;

        generatedTestEmail = faker.internet().emailAddress();
        generatedTestPassword = faker.internet().password();
        generatedTestName = faker.name().firstName();

        responseWithCreatedUser = sendByPost(
                REGISTER_USER_URL,
                new RegisterRequestDto(generatedTestEmail, generatedTestPassword, generatedTestName)
        );
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void positiveLogin(){
        LoginRequestDto pojoJsonForLogin = new LoginRequestDto(
                generatedTestEmail,
                generatedTestPassword
        );

        Response responseWithLoginUser = sendByPost(LOGIN_USER_URL, pojoJsonForLogin);
        checkResponseStatusCode(responseWithLoginUser, SUCCESS_STATUS_CODE);
        checkBodyAfterSuccessfulLogin(responseWithLoginUser, generatedTestEmail, generatedTestName);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void negativeLoginWrongPassword(){
        LoginRequestDto pojoJsonForLogin = new LoginRequestDto(
                generatedTestEmail,
                "Wrong_password"
        );

        Response responseWithUnLoginUser = sendByPost(LOGIN_USER_URL, pojoJsonForLogin);
        checkResponseStatusCode(responseWithUnLoginUser, UNAUTHORIZED_STATUS_CODE);
        checkBodyAfterNegativeLogin(responseWithUnLoginUser);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void negativeLoginWrongEmail(){
        LoginRequestDto pojoJsonForLogin = new LoginRequestDto(
                "wrong-email-839908424023@yandex.ru",
                generatedTestPassword
        );

        Response responseWithUnLoginUser = sendByPost(LOGIN_USER_URL, pojoJsonForLogin);
        checkResponseStatusCode(responseWithUnLoginUser, UNAUTHORIZED_STATUS_CODE);
        checkBodyAfterNegativeLogin(responseWithUnLoginUser);
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(getDtoFromResponseRegisterUser(responseWithCreatedUser).getAccessToken());
    }

}
