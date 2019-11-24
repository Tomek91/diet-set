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
@Table(name = "diet_rate_parameters")
public class DietRateParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Double fastSlimValue;

    @Column(nullable = false)
    private Double averageSlimValue;

    @Column(nullable = false)
    private Double fastPutOnValue;

    @Column(nullable = false)
    private Double averagePutOnValue;
}
