package dto.register.error;


/**
 * <H2>Объект данных для ошибки регистрации пользователя без имени.</H2>
 */
public class RegisterErrorRequestWithoutNameDto {

    private String email;
    private String password;

    public RegisterErrorRequestWithoutNameDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterErrorRequestWithoutNameDto(){}

}
