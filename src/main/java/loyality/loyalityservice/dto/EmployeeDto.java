package loyality.loyalityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class EmployeeDto {
    //Класс для передачи данных между Клиентом и Сервером
    private Long id;
    private String firstName;
    private String email;
    private String phone;
}
