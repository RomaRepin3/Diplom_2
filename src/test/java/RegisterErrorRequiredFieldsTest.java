import com.github.javafaker.Faker;
import dto.register.error.RegisterErrorRequestWithoutEmailDto;
import dto.register.error.RegisterErrorRequestWithoutNameDto;
import dto.register.error.RegisterErrorRequestWithoutPasswordDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static requests.RegisterApiMethods.checkBodyUserRequiredField;
import static requests.RestAssuredBaseMethods.checkResponseStatusCode;
import static requests.RestAssuredBaseMethods.sendByPost;
import static settings.Settings.*;


@RunWith(Parameterized.class)
public class RegisterErrorRequiredFieldsTest {

    private static final Faker faker = new Faker();

    @Parameterized.Parameters
    public static Object[][] testData(){
        return new Object[][] {
                {
                    new RegisterErrorRequestWithoutEmailDto(
                        faker.internet().password(),
                        faker.name().firstName()
                    )
                },
                {
                    new RegisterErrorRequestWithoutNameDto(
                        faker.internet().emailAddress(),
                        faker.internet().password()
                    )
                },
                {
                    new RegisterErrorRequestWithoutPasswordDto(
                            faker.internet().emailAddress(),
                            faker.name().firstName()
                    )
                }
        };
    }

    private final Object pojoJsonData;

    public RegisterErrorRequiredFieldsTest(Object pojoJsonData){
        this.pojoJsonData = pojoJsonData;
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Создать пользователя и не заполнить одно из обязательных полей")
    public void ErrorTryCreateUserWithoutRequiresField(){
        Response response = sendByPost(REGISTER_USER_URL, pojoJsonData);

        checkResponseStatusCode(response, FORBIDDEN_STATUS_CODE);
        checkBodyUserRequiredField(response);
    }

}
