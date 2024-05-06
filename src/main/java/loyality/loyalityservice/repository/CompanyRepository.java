package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.Company;
import loyality.loyalityservice.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {



}
