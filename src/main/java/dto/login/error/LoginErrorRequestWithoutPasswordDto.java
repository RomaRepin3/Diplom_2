package dto.login.error;


/**
 * <H2>Объект ответа при ошибке авторизации пользователя без пароля.</H2>
 */
public class LoginErrorRequestWithoutPasswordDto {

    private String email;

    public LoginErrorRequestWithoutPasswordDto(String email) {
        this.email = email;
    }

    public LoginErrorRequestWithoutPasswordDto(){}

}
