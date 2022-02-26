package recipes.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.recipe.dto.RecipeDto;
import recipes.recipe.dto.RecipeDtoSaved;

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
    @PreAuthorize("hasRole('ROLE_USER')")
    RecipeDto getRecipe(@PathVariable Long id) {
        return service.getRecipeById(id);

    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
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


    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<RecipeDtoSaved> addRecipe(@Valid @RequestBody RecipeDto recipe,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        RecipeDtoSaved recipeSaved = service.addNewRecipe(recipe, userDetails);

        return ResponseEntity.ok(recipeSaved);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<RecipeDto> editRecipe(@PathVariable Long id,
                                         @Valid @RequestBody RecipeDto recipeDto,
                                         @AuthenticationPrincipal UserDetails details) {

        String authorEmail = service.getRecipeAuthorEmail(id);

        if(!details.getUsername().equals(authorEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.editRecipeByID(id, recipeDto, details);

        return ResponseEntity.noContent().build();

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<?> deleteRecipeById(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails details) {

        String authorEmail = service.getRecipeAuthorEmail(id);

        if(!details.getUsername().equals(authorEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.deleteRecipeById(id);

        return ResponseEntity.noContent().build();
    }

}
