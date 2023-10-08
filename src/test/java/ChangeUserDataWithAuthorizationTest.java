import com.github.javafaker.Faker;
import dto.login.success.LoginRequestDto;
import dto.register.success.RegisterRequestDto;
import dto.update.UpdateUserDataRequestWithEmailDto;
import dto.update.UpdateUserDataRequestWithNameDto;
import dto.update.UpdateUserDataRequestWithPasswordDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static settings.Settings.BASE_URL;
import static settings.Settings.LOGIN_USER_URL;
import static settings.Settings.REGISTER_USER_URL;
import static settings.Settings.SUCCESS_STATUS_CODE;
import static settings.Settings.UPDATE_USER_URL;
import static requests.UpdateDataApiMethods.*;
import static requests.LoginApiMethods.getPojoFromResponsePositiveLoginUser;


public class ChangeUserDataWithAuthorizationTest {

    private static final Faker faker = new Faker();

    private String generatedTestEmail;
    private String generatedTestPassword;
    private String generatedTestName;
    private String loginAccessToken;

    @Before
    public void setUp(){
        // Генерация тестовых данных, создание пользователя, авторизация под ним, получения токена
        RestAssured.baseURI = BASE_URL;

        generatedTestEmail = faker.internet().emailAddress();
        generatedTestPassword = faker.internet().password();
        generatedTestName = faker.name().firstName();

        sendByPost(
                REGISTER_USER_URL,
                new RegisterRequestDto(generatedTestEmail, generatedTestPassword, generatedTestName)
        );

        Response responseWithLoginUser = sendByPost(
                LOGIN_USER_URL,
                new LoginRequestDto(generatedTestEmail, generatedTestPassword)
        );

        loginAccessToken = getPojoFromResponsePositiveLoginUser(responseWithLoginUser).getAccessToken();
    }

    @Test
    @DisplayName("Изменение данных пользователя (с авторизацией) для ключа email")
    public void changeUserDataPositiveForEmail(){

        String newEmail = faker.name().firstName().toLowerCase(Locale.ROOT) + generatedTestEmail;

        UpdateUserDataRequestWithEmailDto dtoJsonData = new UpdateUserDataRequestWithEmailDto(
                newEmail
        );

        Response responseWithUpdatedUser = sendByPatchWithToken(UPDATE_USER_URL, dtoJsonData, loginAccessToken);
        checkResponseStatusCode(responseWithUpdatedUser, SUCCESS_STATUS_CODE);
        checkSuccessfulUpdatedUsersData(
                responseWithUpdatedUser,
                newEmail,
                generatedTestName
        );
    }

    @Test
    @DisplayName("Изменение данных пользователя (с авторизацией) для password")
    public void changeUserDataPositiveForPassword(){

        String newPassword = generatedTestPassword + faker.internet().password();

        UpdateUserDataRequestWithPasswordDto dtoJsonData = new UpdateUserDataRequestWithPasswordDto(
                newPassword
        );

        Response responseWithUpdatedUser = sendByPatchWithToken(UPDATE_USER_URL, dtoJsonData, loginAccessToken);
        checkResponseStatusCode(responseWithUpdatedUser, SUCCESS_STATUS_CODE);
        checkSuccessfulUpdatedUsersData(responseWithUpdatedUser, generatedTestEmail, generatedTestName);
    }

    @Test
    @DisplayName("Изменение данных пользователя (с авторизацией) для name")
    public void changeUserDataPositiveForName(){

        String newName = generatedTestName + faker.name().firstName();

        UpdateUserDataRequestWithNameDto dtoJsonData = new UpdateUserDataRequestWithNameDto(
                newName
        );

        Response responseWithUpdatedUser = sendByPatchWithToken(UPDATE_USER_URL, dtoJsonData, loginAccessToken);
        checkResponseStatusCode(responseWithUpdatedUser, SUCCESS_STATUS_CODE);
        checkSuccessfulUpdatedUsersData(
                responseWithUpdatedUser,
                generatedTestEmail,
                newName
        );
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(loginAccessToken);
    }
}