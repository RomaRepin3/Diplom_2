package dto.login.success;


/**
 * <H2>Объект запроса авторизации пользователя.</H2>
 */
public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestDto(){}

}
