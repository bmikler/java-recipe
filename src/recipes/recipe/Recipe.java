package recipes.recipe;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    private LocalDateTime date;
    @NotBlank
    private String description;
    @NotEmpty
    private String[] ingredients;
    @NotEmpty
    private String[] directions;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;


}
