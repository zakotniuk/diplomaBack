package loyality.loyalityservice.entity;


import jakarta.persistence.*;
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


//    @ManyToOne(fetch=FetchType.EAGER)
//    //@Column(name="company_id")
//    @JoinColumn(name = "company_id")
//    private Company company;
    @Column(name="company_id")
    private Long companyId;


    private Integer condition; //кол-во бонусов на счете для перехода в эту группу (должно проверяться при начис/спис бонусов при покупке)
    private Integer discount;//размер скидки в процентах
    @Column(name="is_default")
    private Boolean isDefault;//группа по умолчанию
}
