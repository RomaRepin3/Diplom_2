package dto.register.success;


/**
 * <H2>Объект данных ответа для регистрации пользователя.</H2>
 */
public class RegisterResponseAllDto {

    private boolean success;
    private RegisterResponseUserDto user;
    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

}
