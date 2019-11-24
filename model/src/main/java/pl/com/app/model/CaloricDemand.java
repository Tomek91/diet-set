package pl.com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.DietType;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "caloric_demands")
public class CaloricDemand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Integer nominalValue;

    @Column(nullable = false)
    private Integer dietValue;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DietType dietType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
