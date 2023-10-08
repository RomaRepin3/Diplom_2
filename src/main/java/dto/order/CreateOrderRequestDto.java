package dto.order;

import java.util.List;


/**
 * <H2>Объект данных со списком ингридиентов для создания заказа.</H2>
 */
public class CreateOrderRequestDto {

    private List<String> ingredients;

    public CreateOrderRequestDto(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderRequestDto() {}

}
