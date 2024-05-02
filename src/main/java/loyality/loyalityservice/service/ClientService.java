package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;

import java.util.List;

public interface ClientService {
    ClientDto createClient(Long clientDto, ClientDto companyDto);

    //клиенты ищутся по определенной компании!
    List<ClientDto> getAllClients(Long companyId);

    //клиенты ищутся по определенной компании!
   // List<ClientDto> getAllClients(Long companyId);

}
