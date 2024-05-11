package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public class ClientMapper {
    public static ClientDto mapToClientDto(Client client){
        return new ClientDto(
                client.getId(),
                client.getClientName(),
                client.getEmail(),
                client.getBirthDate(),
                client.getPhone(),
                client.getQrLink(),
                client.getBalance(),
                client.getTotalMoneySpend(),
                client.getCompanyId(),
                client.getGroupId(),

                client.getUpdateDate(),
                client.getBonusSumWriteOn(),
                client.getBonusSumWriteOff()

        );
    }

    public static Client mapToClient(ClientDto clientDto){
        return new Client(
                clientDto.getId(),
                clientDto.getClientName(),
                clientDto.getEmail(),
                clientDto.getBirthDate(),
                clientDto.getPhone(),
                clientDto.getQrLink(),
                clientDto.getBalance(),
                clientDto.getTotalMoneySpend(),
                clientDto.getCompanyId(),
                clientDto.getGroupId(),

                clientDto.getUpdateDate(),
                clientDto.getBonusSumWriteOn(),
                clientDto.getBonusSumWriteOff()
        );
    }
}
