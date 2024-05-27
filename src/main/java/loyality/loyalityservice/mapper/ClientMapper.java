package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.ClientAccount;
import org.mapstruct.Mapper;

@Mapper
public class ClientMapper {
    public static ClientDto mapToClientDto(Client client) {
    return new ClientDto(
            client.getId(),
            client.getClientName(),
            client.getEmail(),
            client.getPassword(),
            client.getBirthDate(),
            client.getPhone()
        );
    }

    public static Client mapToClient(ClientDto clientDto) {
    return new Client(
            clientDto.getId(),
            clientDto.getClientName(),
            clientDto.getEmail(),
            clientDto.getPassword(),
            clientDto.getBirthDate(),
            clientDto.getPhone()
    );
    }
}
