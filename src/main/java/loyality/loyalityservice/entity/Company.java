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
@Table(name= "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="name")
    private String companyName;

    private String email;

    private String password;

    private String phone;

    private String description;

    @Column(name="qr_link")
    private String qrLink;

}
