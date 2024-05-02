package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;

public class ClientMapper {
    public static ClientDto mapToClientDto(Client client){
        return new ClientDto(
                client.getId(),
                client.getClientName(),
                client.getEmail(),
                client.getBirth_date(),
                client.getPhone(),
                client.getQrLink(),
                client.getCompanyId()
        );
    }

    public static Client mapToClient(ClientDto clientDto){
        return new Client(
                clientDto.getId(),
                clientDto.getClientName(),
                clientDto.getEmail(),
                clientDto.getBirth_date(),
                clientDto.getPhone(),
                clientDto.getQrLink(),
                clientDto.getCompanyId()

        );
    }
}
