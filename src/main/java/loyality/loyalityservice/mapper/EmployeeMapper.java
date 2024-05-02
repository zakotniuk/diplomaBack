package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.EmployeeDto;
import loyality.loyalityservice.entity.Employee;

public class EmployeeMapper {

    //класс для сопоставления сущности Employee с EmployeeDto
    //и сопоставления сущности EmployeeDto с Employee
    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getEmail(),
                employee.getPhone()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getEmail(),
                employeeDto.getPhone()
        );
    }

}
