package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.ClientAccount;
import loyality.loyalityservice.mapper.ClientAccountMapper;
import loyality.loyalityservice.mapper.ClientMapper;
import loyality.loyalityservice.repository.ClientRepository;
import loyality.loyalityservice.service.ClientService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;



    @Override
    public ClientDto newClient(ClientDto clientDto) {
        Client client = ClientMapper.mapToClient(clientDto);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.mapToClientDto(savedClient);
    }
}
