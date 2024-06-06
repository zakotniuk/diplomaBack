package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCompanyId(Long companyId);
//    Group findById(Long groupId);


    //@Modifying
    @Query(value = "SELECT id FROM groups g WHERE g.company_id=:companyId AND g.is_default IS TRUE", nativeQuery = true)
    Long getDefGroup(@Param(value="companyId") Long companyId);

    @Query(value = "SELECT id FROM groups g " +
            "WHERE g.company_id=:companyId " +
            "AND g.condition <=:totalClientMonetSpend " +
            "AND g.condition = ( " +
            "    SELECT MAX(condition) " +
            "    FROM groups " +
            "    WHERE company_id=:companyId " +
            "    AND condition <=:totalClientMonetSpend); ", nativeQuery = true)
    Long getActualGroup(@Param(value="companyId") Long companyId, @Param(value="totalClientMonetSpend") Long totalClientMonetSpend);

   // Group findByClientId(Long clientId);
}
