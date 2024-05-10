package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.GroupDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    //клиенты создаются в определенной компании!
    ClientDto createClient(Long companyId, ClientDto clientDto);

    //клиенты ищутся по определенной компании!
    List<ClientDto> getAllClients(Long companyId);

    ClientDto getClientInfo(Long companyId, Long clientId);

    ClientDto updateClient(Long companyId,
                           Long clientId,
                           ClientDto updatedClient,
                           Optional<Double> sum,
                           Optional<String> action);

    Long[] getBonusSum(Long clientId, Double buySum);


}
