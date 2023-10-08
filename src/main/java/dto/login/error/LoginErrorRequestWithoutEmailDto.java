package dto.login.error;


/**
 * <H2>Объект ответа при ошибке авторизации пользователя без email.</H2>
 */
public class LoginErrorRequestWithoutEmailDto {

    private String password;

    public LoginErrorRequestWithoutEmailDto(String password) {
        this.password = password;
    }

    public LoginErrorRequestWithoutEmailDto(){}

}
