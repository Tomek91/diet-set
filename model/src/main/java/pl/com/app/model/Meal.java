package pl.com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long mealId;

    private String title;

    private String image;

    @ElementCollection
    @CollectionTable(
            name = "imageUrls",
            joinColumns = @JoinColumn(name = "image_url_id")
    )
    @Column(name = "image_url")
    private List<String> imageUrls;

    private Integer readyInMinutes;

    private Integer servings;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "meals")
    private Set<MealPlan> mealPlans;
}
