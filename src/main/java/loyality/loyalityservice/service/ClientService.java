package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;

import java.util.List;

public interface ClientService {
    //клиенты создаются в определенной компании!
    ClientDto createClient(Long companyId, ClientDto clientDto);

    //клиенты ищутся по определенной компании!
    List<ClientDto> getAllClients(Long companyId);

}
