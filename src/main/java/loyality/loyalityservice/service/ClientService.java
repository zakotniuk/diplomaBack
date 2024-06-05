package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.ClientDto;
import org.springframework.stereotype.Service;


public interface ClientService {



    ClientDto newClient(ClientDto clientDto);

    ClientDto getClientById(Long clientId);


}
