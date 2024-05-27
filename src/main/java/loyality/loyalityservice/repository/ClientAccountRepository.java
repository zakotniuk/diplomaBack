package loyality.loyalityservice.repository;

import jakarta.transaction.Transactional;
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
    @Query(value = "UPDATE client_account SET \"group_id\"=:newGroupId WHERE \"company_id\"=:companyId AND \"group_id\"=:oldGroupId", nativeQuery = true)
    void setGroup(@Param(value="companyId") Long companyId,
                  @Param(value="oldGroupId") Long oldGroupId,
                  @Param(value="newGroupId") Long newGroupId);


}
