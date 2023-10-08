import com.github.javafaker.Faker;
import dto.login.error.LoginErrorRequestWithoutEmailDto;
import dto.login.error.LoginErrorRequestWithoutPasswordDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static requests.LoginApiMethods.checkBodyAfterNegativeLogin;
import static requests.RestAssuredBaseMethods.checkResponseStatusCode;
import static requests.RestAssuredBaseMethods.sendByPost;
import static settings.Settings.*;

@RunWith(Parameterized.class)
public class LoginNegativeWithoutOneOfFieldTest {

    private static final Faker faker = new Faker();

    @Parameterized.Parameters
    public static Object[][] testData(){
        return new Object[][] {
                {new LoginErrorRequestWithoutEmailDto(faker.internet().password())},
                {new LoginErrorRequestWithoutPasswordDto(faker.internet().emailAddress())}
        };
    }

    private final Object pojoJsonData;
    public LoginNegativeWithoutOneOfFieldTest(Object pojoJsonData){
        this.pojoJsonData = pojoJsonData;
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем (без одного из полей)")
    public void negativeTryCreateUserWithoutRequiresField(){
        Response responseWithUnLoginUser = sendByPost(LOGIN_USER_URL, pojoJsonData);

        checkResponseStatusCode(responseWithUnLoginUser, UNAUTHORIZED_STATUS_CODE);
        checkBodyAfterNegativeLogin(responseWithUnLoginUser);
    }
}
