package loyality.loyalityservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;

    private String clientName;
    private String email;
    private String password;
    private String birthDate;
    private String phone;

}
