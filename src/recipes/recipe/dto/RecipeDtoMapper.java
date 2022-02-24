package recipes.recipe.dto;

import org.springframework.stereotype.Component;
import recipes.recipe.Recipe;

@Component
public class RecipeDtoMapper {

    public RecipeDtoSaved mapSaved(Recipe recipe) {

        return new RecipeDtoSaved(recipe.getId());

    }

    public Recipe map(RecipeDto recipe){

        return new Recipe(
                null,
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDate(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections(),
                null
        );

    }

    public RecipeDto map(Recipe recipe) {

        return new RecipeDto(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getDate(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getDirections()
        );

    }

}
