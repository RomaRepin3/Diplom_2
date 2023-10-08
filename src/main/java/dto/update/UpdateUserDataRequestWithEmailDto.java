package dto.update;


/**
 * <H2>Объект данных для обновления email пользователя.</H2>
 */
public class UpdateUserDataRequestWithEmailDto {

    private String email;

    public UpdateUserDataRequestWithEmailDto(String email) {
        this.email = email;
    }

    public UpdateUserDataRequestWithEmailDto() {}

}
