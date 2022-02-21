package recipes;

import recipes.dto.RecipeDto;
import recipes.dto.RecipeDtoMapper;
import recipes.dto.RecipeDtoSaved;

@org.springframework.stereotype.Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoMapper recipeDtoMapper;

    public RecipeService(RecipeRepository recipeRepository, RecipeDtoMapper recipeDtoMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
    }

    public RecipeDto getRecipeById(Long id) {

        return recipeRepository.findById(id)
                .map(recipeDtoMapper::map)
                .orElseThrow(RecipeNotFoundException::new);
    }

    public RecipeDtoSaved addNewRecipe(RecipeDto recipeDto) {

        Recipe recipe = recipeDtoMapper.map(recipeDto);

        Recipe recipeSaved = recipeRepository.save(recipe);
        return recipeDtoMapper.mapSaved(recipeSaved);

    }

    public void deleteRecipeById(Long id) {

        recipeRepository.findById(id)
                .ifPresentOrElse(
                    recipeRepository::delete,
                    () -> {throw new RecipeNotFoundException();}
                );

    }
}
