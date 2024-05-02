package loyality.loyalityservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name= "employees")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//автоинкремент
    private Long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="email")
    private String email;

    private String phone;
}
