package dto.ingredients;

import java.util.List;


/**
 * <H2>Объект хранения всех ингредиентов.</H2>
 */
public class IngredientsResponseAllDto {

    private String success;
    private List<IngredientsResponseDataDto> data;

    public IngredientsResponseAllDto(String success, List<IngredientsResponseDataDto> data) {
        this.success = success;
        this.data = data;
    }

    public IngredientsResponseAllDto() {}

    public List<IngredientsResponseDataDto> getData() {
        return data;
    }

}
