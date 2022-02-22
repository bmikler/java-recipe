package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RecipeDto;
import recipes.dto.RecipeDtoSaved;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/search")
    List<RecipeDto> findRecipeByCategoryOrName (@RequestParam(required = false) String category,
                                                @RequestParam(required = false) String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (category != null) {
            return service.findByCategory(category);
        }

        return service.findByName(name);

    }

    @PutMapping("/{id}")
    ResponseEntity<RecipeDto> editRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {

        service.editRecipeByID(id, recipeDto);

        return ResponseEntity.noContent().build();

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
