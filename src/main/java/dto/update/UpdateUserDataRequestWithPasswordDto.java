package dto.update;


/**
 * <H2>Объект данных для обновления пароля пользователя.</H2>
 */
public class UpdateUserDataRequestWithPasswordDto {

    private String password;

    public UpdateUserDataRequestWithPasswordDto(String password) {
        this.password = password;
    }

    public UpdateUserDataRequestWithPasswordDto() {}

}
