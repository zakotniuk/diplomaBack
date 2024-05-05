package loyality.loyalityservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

//    @OneToMany(cascade = CascadeType.ALL)//объект Company может хранить у себя много объектов Group
//    @JoinColumn(name = "company_id") //мы указали, в какой колонке таблицы group хранится id объекта Company.
//    private Set<Group> groups = new HashSet<Group>();


}
