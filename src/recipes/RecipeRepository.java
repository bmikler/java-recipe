package recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findRecipeByCategoryIgnoreCase(String category);

    List<Recipe> findRecipeByNameContainsIgnoreCase(String name);

}
