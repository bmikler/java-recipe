package recipes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.dto.RecipeDto;
import recipes.dto.RecipeDtoSaved;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    RecipeDto getRecipe(@PathVariable Long id) {

        return service.getRecipeById(id);

    }

    @PostMapping("/new")
    ResponseEntity<RecipeDtoSaved> addRecipe(@Valid @RequestBody RecipeDto recipe) {

        RecipeDtoSaved recipeSaved = service.addNewRecipe(recipe);

        return ResponseEntity.ok(recipeSaved);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRecipeById(@PathVariable Long id) {

        service.deleteRecipeById(id);

        return ResponseEntity.noContent().build();
    }

}
