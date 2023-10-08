import com.github.javafaker.Faker;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static requests.RegisterApiMethods.getDtoFromResponseRegisterUser;
import static requests.RestAssuredBaseMethods.*;
import static requests.UpdateDataApiMethods.checkBodyAfterUnsuccessfulUpdatedUser;
import static settings.Settings.*;


@RunWith(Parameterized.class)
public class ChangeUserDataWithoutAuthorizationTest {

    private static final Faker faker = new Faker();

    @Parameterized.Parameters
    public static Object[][] getData(){
        return new Object[][] {
                {new UpdateUserDataRequestWithEmailDto(faker.internet().emailAddress())},
                {new UpdateUserDataRequestWithNameDto(faker.name().firstName())},
                {new UpdateUserDataRequestWithPasswordDto(faker.internet().password())},
        };
    }
    private final Object pojoJsonData;
    public ChangeUserDataWithoutAuthorizationTest(Object pojoJsonData){
        this.pojoJsonData = pojoJsonData;
    }

    private String loginAccessToken;

    @Before
    public void setUp(){
        // Генерация тестовых данных, создание пользователя, получения токена
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
    }

    @Test
    @DisplayName("Изменение данных пользователя (без авторизации)")
    public void changeUserDataNegative(){
        Response responseWithoutUpdatedUser = sendByPatch(UPDATE_USER_URL, pojoJsonData);

        checkResponseStatusCode(responseWithoutUpdatedUser, UNAUTHORIZED_STATUS_CODE);
        checkBodyAfterUnsuccessfulUpdatedUser(responseWithoutUpdatedUser);
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(loginAccessToken);
    }
}