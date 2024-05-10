package loyality.loyalityservice.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="name")
    private String groupName;

    @Column(name="company_id")
    private Long companyId;

    private Integer condition; //кол-во бонусов на счете для перехода в эту группу (должно проверяться при начис/спис бонусов при покупке)
    private Integer discount;//размер скидки в процентах (0-100%)
    private Integer availablePart; //часть, которую можно покрыть баллами (0-100%)
    @Column(name="is_default")
    private Boolean isDefault;//группа по умолчанию
}
