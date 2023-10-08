package requests;


import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static requests.Settings.*;


/**
 * <H2>Базовый класс для работы с запросами.</H2>
 */
public abstract class RestAssuredBaseMethods {

    @Step("Отправка запроса на API методом POST с телом (без авторизаци)")
    public static Response sendByPost(String url, Object body){
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(body)
                .when()
                .post(url);
    }

    @Step("Отправка запроса на API методом POST с телом и Bearer Token")
    public static Response sendByPostWithToken(String url, Object body, String bearerToken){
        return given()
                .header("Authorization", bearerToken)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(body)
                .when()
                .post(url);
    }

    @Step("Отправка запроса на API методом GET без тела и авторизаци")
    public static Response sendByGet(String url){
        return given()
                .when()
                .get(url);
    }

    @Step("Отправка запроса на API методом GET без тела, но с авторизацией")
    public static Response sendByGetWithToken(String url, String bearerToken){
        return given()
                .header("Authorization", bearerToken)
                .when()
                .get(url);
    }

    @Step("Отправка запроса на API методом PATCH с телом (без авторизаци)")
    public static Response sendByPatch(String url, Object body){
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(body)
                .when()
                .patch(url);
    }

    @Step("Отправка запроса на API методом PATCH с телом и Bearer Token")
    public static Response sendByPatchWithToken(String url, Object body, String bearerToken){
        return given()
                .header("Authorization", bearerToken)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(body)
                .when()
                .patch(url);
    }

    @Step("Удаление пользователя при помощи Bearer Token")
    public static void deleteUserByBearerToken(String bearerToken){
        given()
                .header("Authorization", bearerToken)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .when()
                .delete(DELETE_USER_URL);
    }

    @Step("Проверка статус кода с ожидаемым")
    public static void checkResponseStatusCode(Response response, int statusCode){
        response.then().statusCode(statusCode);
    }

    @Step("Проверка наличия в ответе от API нужного ключа со значением")
    public static void checkResponseBodyJson(Response response, String jsonKey, String jsonValue){
        response.then().assertThat().body(jsonKey, equalTo(jsonValue));
    }

    @Step("Проверка наличия в ответе от API нужного ключа со значением")
    public static void checkResponseBodyJson(Response response, String jsonKey, boolean jsonValue){
        response.then().assertThat().body(jsonKey, equalTo(jsonValue));
    }

    @Step("Проверка наличия в ответе от API нужного ключа со значением")
    public static void checkResponseBodyJson(Response response, String jsonKey, List<String> jsonValue){
        response.then().assertThat().body(jsonKey, hasItems(jsonValue));
    }

    @Step("Проверка наличия в ответе от API, что значение ключа является строкой")
    public static void checkResponseBodyJsonNotNullKey(Response response, String jsonKey){
        response.then().assertThat().body(jsonKey, notNullValue());
    }

}
