package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.entity.ClientAccount;
import org.mapstruct.Mapper;

@Mapper
public class ClientAccountMapper {
    public static ClientAccountDto mapToClientAccountDto(ClientAccount clientAccount){
        return new ClientAccountDto(
                clientAccount.getId(),
                clientAccount.getCompanyId(),
                clientAccount.getGroupId(),
                clientAccount.getClientId(),

                clientAccount.getQrLink(),
                clientAccount.getBalance(),
                clientAccount.getTotalMoneySpend(),

                clientAccount.getUpdateDate(),
                clientAccount.getBonusSumWriteOn(),
                clientAccount.getBonusSumWriteOff()

        );
    }

    public static ClientAccount mapToClientAccount(ClientAccountDto clientAccountDto){
        return new ClientAccount(
                clientAccountDto.getId(),
                clientAccountDto.getCompanyId(),
                clientAccountDto.getGroupId(),
                clientAccountDto.getClientId(),

                clientAccountDto.getQrLink(),
                clientAccountDto.getBalance(),
                clientAccountDto.getTotalMoneySpend(),

                clientAccountDto.getUpdateDate(),
                clientAccountDto.getBonusSumWriteOn(),
                clientAccountDto.getBonusSumWriteOff()
        );
    }
}
