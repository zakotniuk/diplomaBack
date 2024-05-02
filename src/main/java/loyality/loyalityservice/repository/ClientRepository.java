package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByCompanyId(Long companyId);
}
