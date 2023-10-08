import com.github.javafaker.Faker;
import dto.register.success.RegisterRequestDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static requests.RegisterApiMethods.*;
import static settings.Settings.*;


public class RegisterTest {

    private static final Faker faker = new Faker();

    private String generatedTestEmail;
    private String generatedTestName;
    private RegisterRequestDto positiveRegister;
    private Response responseWithCreatedUser;

    @Before
    public void setUp(){
        // Генерация тестовых данных
        RestAssured.baseURI = BASE_URL;

        generatedTestEmail = faker.internet().emailAddress();
        generatedTestName = faker.name().firstName();

        positiveRegister = new RegisterRequestDto(
                generatedTestEmail,
                faker.internet().password(),
                generatedTestName
        );
    }

    @Test
    @DisplayName("Создать уникального пользователя")
    public void createUniqueUserTest(){
        responseWithCreatedUser = sendByPost(REGISTER_USER_URL, positiveRegister);

        checkResponseStatusCode(responseWithCreatedUser, SUCCESS_STATUS_CODE);
        checkBodyAfterSuccessfulRegister(responseWithCreatedUser, generatedTestEmail, generatedTestName);
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void negativeTryCreateAlreadyExistUserTest(){
        responseWithCreatedUser = sendByPost(REGISTER_USER_URL, positiveRegister);
        Response responseExitUser = sendByPost(REGISTER_USER_URL, positiveRegister);

        checkResponseStatusCode(responseExitUser, FORBIDDEN_STATUS_CODE);
        checkBodyUserAlreadyExist(responseExitUser);
    }

    @After
    public void deleteUser(){
        // Удаление пользователя
        deleteUserByBearerToken(getDtoFromResponseRegisterUser(responseWithCreatedUser).getAccessToken());
    }

}
