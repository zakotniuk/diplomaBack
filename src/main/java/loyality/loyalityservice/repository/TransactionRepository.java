package loyality.loyalityservice.repository;

import jakarta.transaction.Transactional;
import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCompanyId(Long companyId);

    //List<Transaction> findByCompanyId
    @Query(value = "SELECT * FROM transaction t WHERE t.company_id=:companyId LIMIT :limit", nativeQuery = true)
    List<Transaction> getLimitedCountTransactions(@Param(value="companyId") Long companyId, @Param(value="limit") Integer limit);

    @Query(value = "SELECT * FROM transaction t WHERE t.company_id=:companyId AND t.create_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Transaction> getTransactionsByPeriod(@Param(value="companyId") Long companyId,
                                              @Param(value="startDate") String startDate,
                                              @Param(value="endDate") String endDate
                                              );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM transaction t WHERE t.create_date < :dateX", nativeQuery = true)
    void dropTransactionsByDateX(@Param(value="dateX") String dateX);

}
