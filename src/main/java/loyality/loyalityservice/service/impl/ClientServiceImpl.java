package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.ClientMapper;
import loyality.loyalityservice.repository.ClientRepository;
import loyality.loyalityservice.repository.CompanyRepository;
import loyality.loyalityservice.repository.GroupRepository;
import loyality.loyalityservice.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    private GroupRepository groupRepository;
    @Override
    //клиент создается в определенной компании!
    public ClientDto createClient(Long companyId, ClientDto clientDto) {

        Client client = ClientMapper.mapToClient(clientDto);
        client.setCompanyId(companyId);
        client.setBalance(0L);
        client.setTotalMoneySpend(0L);

        //ищем дефолтную группу компании, которая должна присвоиться клиенту по умолчанию
        Long gId = groupRepository.getDefGroup(companyId);
        //задаем группу по умолчанию (в рамках конкретной компании)
        client.setGroupId(gId);

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

    @Override
    public ClientDto getClientInfo(Long companyId, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->
                    new ResourceNotFoundException("Указанного клиента не существует. ID="+ clientId));
        return ClientMapper.mapToClientDto(client);
    }
}
