package dto.login.success;


/**
 * <H2>Объект данных для авторизации пользователя.</H2>
 */
public class LoginResponseAllDto {

    private boolean success;
    private String accessToken;
    private String refreshToken;
    private LoginResponseUserDto user;

    public String getAccessToken() {
        return accessToken;
    }

}
