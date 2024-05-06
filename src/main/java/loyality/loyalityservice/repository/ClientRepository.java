package loyality.loyalityservice.repository;

import jakarta.transaction.Transactional;
import loyality.loyalityservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {

    //метод для поиска списка клиентов по заданной компании
    List<Client> findByCompanyId(Long companyId);

    List<Client> findByCompanyIdAndGroupId(Long companyId, Long groupId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE client SET \"group_id\"=:newGroupId WHERE \"company_id\"=:companyId AND \"group_id\"=:oldGroupId", nativeQuery = true)
    void setGroup(@Param(value="companyId") Long companyId,
                  @Param(value="oldGroupId") Long oldGroupId,
                  @Param(value="newGroupId") Long newGroupId);
}
