package loyality.loyalityservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String clientName;
    private String email;
    private Date birth_date;
    private String phone;
    private String qrLink;
    private Long companyId;
}
