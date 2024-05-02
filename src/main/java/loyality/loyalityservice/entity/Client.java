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
@Table(name= "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="name")
    private String clientName;
    private String email;
    private Date birth_date;
    private String phone;
    @Column(name="qr_link")
    private String qrLink;
    @Column(name="company_id")
    private Long companyId;

}
