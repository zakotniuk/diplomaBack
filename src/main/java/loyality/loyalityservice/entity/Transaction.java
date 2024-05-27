package loyality.loyalityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

//    @Column(name="transaction_id")
//    private String transactionId; //идентификатор операции

    @Column(name="company_id")
    private Long companyId;

    @Column(name="client_account_id")
    private Long clientAccountId;

    private String action; //действие (начисление/списание)

    private Double sum; //сумма действия

    @Column(name="create_date")
    private String createDate; //дата совершения операции





}
