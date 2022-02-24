package recipes.recipe;

import recipes.recipe.dto.RecipeDto;
import recipes.recipe.dto.RecipeDtoMapper;
import recipes.recipe.dto.RecipeDtoSaved;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<RecipeDto> findByCategory(String category) {

        return recipeRepository.findRecipeByCategoryIgnoreCase(category)
                .stream()
                .map(recipeDtoMapper::map)
                .sorted(Comparator.comparing(RecipeDto::getDate).reversed())
                .collect(Collectors.toList());

    }

    public List<RecipeDto> findByName(String name) {

        return recipeRepository.findRecipeByNameContainsIgnoreCase(name)
                .stream()
                .map(recipeDtoMapper::map)
                .sorted(Comparator.comparing(RecipeDto::getDate).reversed())
                .collect(Collectors.toList());

    }

    public RecipeDtoSaved addNewRecipe(RecipeDto recipeDto) {

        Recipe recipe = recipeDtoMapper.map(recipeDto);
        recipe.setDate(LocalDateTime.now());

        Recipe recipeSaved = recipeRepository.save(recipe);
        return recipeDtoMapper.mapSaved(recipeSaved);

    }

    public RecipeDto editRecipeByID(Long id, RecipeDto recipeDto) {

        if(recipeRepository.findById(id).isEmpty()){
            throw new RecipeNotFoundException();
        }

        Recipe recipe = recipeDtoMapper.map(recipeDto);
        recipe.setId(id);
        recipe.setDate(LocalDateTime.now());

        Recipe recipeSaved = recipeRepository.save(recipe);

        return recipeDtoMapper.map(recipeSaved);

    }

    public void deleteRecipeById(Long id) {

        recipeRepository.findById(id)
                .ifPresentOrElse(
                    recipeRepository::delete,
                    () -> {throw new RecipeNotFoundException();}
                );

    }
}
