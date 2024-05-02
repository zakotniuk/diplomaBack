package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//интерфейс репозитория для "сотрудников", который расширяет интерфейс репозитория JPA
    //=> интерфейс получит грубые методы для операций с БД

}
