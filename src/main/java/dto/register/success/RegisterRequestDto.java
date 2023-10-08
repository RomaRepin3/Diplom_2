package dto.register.success;


/**
 * <H2>Объект данных запроса для регистрации пользователя.</H2>
 */
public class RegisterRequestDto {

    private String email;
    private String password;
    private String name;

    public RegisterRequestDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public RegisterRequestDto(){}

}
