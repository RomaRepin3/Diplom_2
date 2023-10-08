package dto.ingredients;


/**
 * <H2>Объект для хранения одного ингредиента.</H2>
 */
public class IngredientsResponseDataDto {

    private String _id;
    private String name;
    private String type;
    private int proteins;
    private int fat;
    private int carbohydrates;
    private int calories;
    private int price;
    private String image;
    private String image_mobile;
    private String image_large;
    private int __v;

    public IngredientsResponseDataDto(
            String _id,
            String name,
            String type,
            int proteins,
            int fat,
            int carbohydrates,
            int calories,
            int price,
            String image,
            String image_mobile,
            String image_large,
            int __v
    ) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.price = price;
        this.image = image;
        this.image_mobile = image_mobile;
        this.image_large = image_large;
        this.__v = __v;
    }

    public IngredientsResponseDataDto() {}

    public String getId() {
        return _id;
    }
}
