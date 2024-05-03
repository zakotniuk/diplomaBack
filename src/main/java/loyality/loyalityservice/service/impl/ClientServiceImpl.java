package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.mapper.ClientMapper;
import loyality.loyalityservice.repository.ClientRepository;
import loyality.loyalityservice.repository.CompanyRepository;
import loyality.loyalityservice.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    @Override
    //клиент создается в определенной компании!
    public ClientDto createClient(Long companyId, ClientDto clientDto) {
        Client client = ClientMapper.mapToClient(clientDto);
        client.setCompanyId(companyId);
        client.setBalance(0);
        Client savedClient = clientRepository.save(client);

        return ClientMapper.mapToClientDto(savedClient);
    }



    @Override
    //клиенты ищутся по определенной компании!
    public List<ClientDto> getAllClients(Long companyId) {
        List<Client> clients = clientRepository.findByCompanyId(companyId);
        return clients.stream().map((client) -> ClientMapper.mapToClientDto(client))
                .collect(Collectors.toList());
    }
}
