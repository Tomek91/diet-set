package pl.com.app.model;

import lombok.*;
import pl.com.app.model.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private LocalDate birthday;

    private String email;

    private Boolean active;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CaloricDemand caloricDemand;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserMealParameter userMealParameter;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RecipeByParameter> mealParameters;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MealPlan> mealPlans;
}
