package loyality.loyalityservice.repository;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import loyality.loyalityservice.dto.CliAccDtoForFront;
import loyality.loyalityservice.entity.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {

    //метод для поиска списка счетов клиентов по заданной компании
    List<ClientAccount> findByCompanyId(Long companyId);
    List<ClientAccount> findByClientId(Long clientId);

    List<ClientAccount> findByCompanyIdAndGroupId(Long companyId, Long groupId);
    List<ClientAccount> findByCompanyIdAndClientId(Long companyId, Long clientId);

    @Transactional
    @Modifying
    //метод для изменения группы у счетов клиентов по заданной компании
    @Query(value = "SELECT c.id AS id, g.name AS name, c.balance as balance, c.total_money AS total_money, c.client_id AS client_id\n" +
            "FROM client_account c \n" +
            "INNER JOIN groups g ON c.group_id = g.id WHERE c.company_id=:companyId \n" , nativeQuery = true)
    List<Tuple> getAllAcc(@Param(value="companyId") Long companyId);


    @Transactional
    @Modifying
    //метод для изменения группы у счетов клиентов по заданной компании
    @Query(value = "UPDATE client_account SET \"group_id\"=:newGroupId WHERE \"company_id\"=:companyId AND \"group_id\"=:oldGroupId", nativeQuery = true)
    void setGroup(@Param(value="companyId") Long companyId,
                  @Param(value="oldGroupId") Long oldGroupId,
                  @Param(value="newGroupId") Long newGroupId);


}
