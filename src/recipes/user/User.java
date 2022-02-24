package recipes.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.recipe.Recipe;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToMany
    private Set<Recipe> recipes;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
