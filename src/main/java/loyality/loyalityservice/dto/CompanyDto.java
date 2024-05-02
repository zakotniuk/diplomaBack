package loyality.loyalityservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CompanyDto {
    //Класс для передачи данных между Клиентом и Сервером

    private Long id;

    private String companyName;

    private String email;

    private String password;

    private String phone;

    private String description;

    private String qrLink;
}
