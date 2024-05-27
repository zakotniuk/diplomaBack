package loyality.loyalityservice.repository;

import loyality.loyalityservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long>   {



}
