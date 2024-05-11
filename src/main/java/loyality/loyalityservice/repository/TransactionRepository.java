package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCompanyId(Long companyId);



}
