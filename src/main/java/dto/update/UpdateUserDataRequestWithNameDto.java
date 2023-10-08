package dto.update;


/**
 * <H2>Объект данных для обновления имени пользователя.</H2>
 */
public class UpdateUserDataRequestWithNameDto {

    private String name;

    public UpdateUserDataRequestWithNameDto(String name) {
        this.name = name;
    }

    public UpdateUserDataRequestWithNameDto() {}

}
