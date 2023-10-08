package requests;

import dto.ingredients.IngredientsResponseAllDto;
import dto.ingredients.IngredientsResponseDataDto;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static settings.Settings.ALL_INGREDIENTS_URL;


/**
 * <H2>Проверки тела ответа для ингрединетов.</H2>
 */
public class IngredientsApiMethods extends RestAssuredBaseMethods {

    @Step("Получение Pojo со всеми ингридиентами заказа из API")
    public static IngredientsResponseAllDto getAllIngredientsPojo(){
        return sendByGet(ALL_INGREDIENTS_URL).as(IngredientsResponseAllDto.class);
    }

    @Step("Получение списка всех хешей ингридиентов")
    public static List<String> getListOfIngredientsId(){
        List<String> listOfAllId = new ArrayList<>();

        List<IngredientsResponseDataDto> listOfAllIngredientsData = getAllIngredientsPojo().getData();
        for (IngredientsResponseDataDto ingredient: listOfAllIngredientsData){
            listOfAllId.add(ingredient.getId());
        }
        return listOfAllId;
    }

    @Step("Получение одного рандомного хеша ингридиента")
    public static List<String> getRandomOneIngredient(List<String> listOfAllId) {
        List<String> randomOneIngredient = new ArrayList<>();

        int randomIndex = new Random().nextInt(listOfAllId.size());
        randomOneIngredient.add(listOfAllId.get(randomIndex));
        return randomOneIngredient;
    }

    @Step("Получение несколиких рандомных хешей ингридиентов")
    public static List<String> getRandomIngredients(List<String> listOfAllId, int count){
        if (listOfAllId.size() < count || listOfAllId.isEmpty() || count <= 0){
            return getRandomOneIngredient(listOfAllId);
        }

        List<String> randomIngredients = new ArrayList<>();
        for (int i = 0; i <= count; i++){
            int randomIndex = new Random().nextInt(listOfAllId.size());
            randomIngredients.add(listOfAllId.get(randomIndex));
        }
        return randomIngredients;
    }

}
