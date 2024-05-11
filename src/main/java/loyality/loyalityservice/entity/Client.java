package loyality.loyalityservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="name")
    private String clientName;
    private String email;
    @Column(name="birth_date")
    private String birthDate;
    private String phone;
    @Column(name="qr_link")
    private String qrLink;
    private Long balance;//баллы клиента
    @Column(name="total_money")
    private Long totalMoneySpend; //сумма всех покупок за все время

    @Column(name="company_id")
    private Long companyId;

    @Column(name="group_id")
    private Long groupId;


    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updateDate;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long bonusSumWriteOn;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long bonusSumWriteOff;

}
