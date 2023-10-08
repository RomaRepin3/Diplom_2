package dto.register.error;


/**
 * <H2>Объект данных для автризации пользовтаеля бех пароля.</H2>
 */
public class RegisterErrorRequestWithoutPasswordDto {

    private String email;
    private String name;

    public RegisterErrorRequestWithoutPasswordDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public RegisterErrorRequestWithoutPasswordDto(){}

}
