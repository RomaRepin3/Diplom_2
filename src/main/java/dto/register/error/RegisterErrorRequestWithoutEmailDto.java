package dto.register.error;


/**
 * <H2>Объект данных негативной регистрации пользователя без email.</H2>
 */
public class RegisterErrorRequestWithoutEmailDto {

    private String password;
    private String name;

    public RegisterErrorRequestWithoutEmailDto(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public RegisterErrorRequestWithoutEmailDto(){}

}
