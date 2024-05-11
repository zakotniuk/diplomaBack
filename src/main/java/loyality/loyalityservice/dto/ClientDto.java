package loyality.loyalityservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String clientName;
    private String email;
    private String birthDate;
    private String phone;
    private String qrLink;
    private Long balance;//баллы клиента
    private Long totalMoneySpend; //сумма всех покупок за все время
    private Long companyId;
    private Long groupId;


    private Date updateDate;
    private Long bonusSumWriteOn;
    private Long bonusSumWriteOff;

}
