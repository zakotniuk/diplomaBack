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
@Table(name= "client")

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="name")
    private String clientName;
    private String email;
    private String password;
    @Column(name="birth_date")
    private String birthDate;
    private String phone;


}
