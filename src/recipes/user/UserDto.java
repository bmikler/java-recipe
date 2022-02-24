package recipes.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.recipe.Recipe;

import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Email
    private String email;
    @Size(min = 8)
    private String password;



}
