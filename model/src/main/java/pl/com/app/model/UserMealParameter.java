package pl.com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.Cuisine;
import pl.com.app.model.enums.Diet;
import pl.com.app.model.enums.Ingredient;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_meal_parameter")
public class UserMealParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "cuisines",
            joinColumns = @JoinColumn(name = "user_meal_parameter_id")
    )
    @Column(name = "cuisine")
    @Enumerated(EnumType.STRING)
    private Set<Cuisine> cuisines;

    @ElementCollection
    @CollectionTable(
            name = "exclude_cuisines",
            joinColumns = @JoinColumn(name = "user_meal_parameter_id")
    )
    @Column(name = "excludeCuisine")
    @Enumerated(EnumType.STRING)
    private Set<Cuisine> excludeCuisines;

    @Enumerated(EnumType.STRING)
    private Diet diet;

    @ElementCollection
    @CollectionTable(
            name = "include_ingredients",
            joinColumns = @JoinColumn(name = "user_meal_parameter_id")
    )
    @Column(name = "includeIngredient")
    @Enumerated(EnumType.STRING)
    private Set<Ingredient> includeIngredients;

    @ElementCollection
    @CollectionTable(
            name = "exclude_ingredients",
            joinColumns = @JoinColumn(name = "user_meal_parameter_id")
    )
    @Column(name = "excludeIngredient")
    @Enumerated(EnumType.STRING)
    private Set<Ingredient> excludeIngredients;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
