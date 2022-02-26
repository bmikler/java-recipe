package recipes.recipe;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import recipes.recipe.dto.RecipeDto;
import recipes.recipe.dto.RecipeDtoMapper;
import recipes.recipe.dto.RecipeDtoSaved;
import recipes.user.User;
import recipes.user.UserRepository;
import recipes.user.auth.CredentialsException;
import recipes.user.service.WrongEmailException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoMapper recipeDtoMapper;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeDtoMapper recipeDtoMapper, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeDtoMapper = recipeDtoMapper;
        this.userRepository = userRepository;
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

    public RecipeDtoSaved addNewRecipe(RecipeDto recipeDto, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(CredentialsException::new);

        Recipe recipe = recipeDtoMapper.map(recipeDto);
        recipe.setDate(LocalDateTime.now());
        recipe.setAuthor(user);

        Recipe recipeSaved = recipeRepository.save(recipe);
        return recipeDtoMapper.mapSaved(recipeSaved);

    }

    public RecipeDto editRecipeByID(Long id, RecipeDto recipeDto, UserDetails details) {

        Recipe current = recipeRepository.findById(id)
                .orElseThrow(RecipeNotFoundException::new);

        Recipe recipe = recipeDtoMapper.map(recipeDto);
        recipe.setId(id);
        recipe.setDate(LocalDateTime.now());
        recipe.setAuthor(current.getAuthor());

        Recipe recipeSaved = recipeRepository.save(recipe);

        return recipeDtoMapper.map(recipeSaved);

    }

    public void deleteRecipeById(Long id) {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(RecipeNotFoundException::new);

        recipeRepository.delete(recipe);

    }

    public String getRecipeAuthorEmail(Long recipeId) {

        return recipeRepository.findById(recipeId)
                .map(Recipe::getAuthor)
                .map(User::getEmail)
                .orElseThrow(RecipeNotFoundException::new);

    }

}
