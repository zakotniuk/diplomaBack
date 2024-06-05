package loyality.loyalityservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CliAccDtoForFront {

    private Long id;
    private String name;
    private Long balance;
    private Long total_money;
    private Long client_id;
    //private String clientName;
    //private String email;
    // private String birthDate;
    // private String phone;
//
//    private Long companyId;
//    private Long groupId;
//    private Long clientId;
//
//    private String qrLink;
//    private Long balance;//баллы клиента
//    private Long totalMoneySpend; //сумма всех покупок за все время
//
//    private Date updateDate;
//    private Long bonusSumWriteOn;
//    private Long bonusSumWriteOff;
}
