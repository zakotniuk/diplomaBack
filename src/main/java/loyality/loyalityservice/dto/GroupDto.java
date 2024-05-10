package loyality.loyalityservice.dto;


import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import loyality.loyalityservice.entity.Company;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;
    private String groupName;
   // private Company company;
    private Long companyId;
    private Integer condition; //кол-во бонусов на счете для перехода в эту группу (должно проверяться при начис/спис бонусов при покупке)
    private Integer discount;//размер скидки в процентах
    private Integer availablePart; //часть, которую можно покрыть баллами (0-100%)
    private Boolean isDefault;//группа по умолчанию
}
