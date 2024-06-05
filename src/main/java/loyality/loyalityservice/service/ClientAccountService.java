package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.CliAccDtoForFront;
import loyality.loyalityservice.dto.ClientAccountDto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientAccountService {
    //счет клиента создается в определенной компании!
    ClientAccountDto createClientAccount(Long companyId, ClientAccountDto clientAccountDto);
    List<ClientAccountDto> getAllClientAccountsByClient(Long clientId);


    //клиенты ищутся по определенной компании!
    List<CliAccDtoForFront> getAllClientAccounts(Long companyId);

    //клиенты ищутся по определенной компании!
   // List<ClientAccountDto> getAllClientAccountsByCompanyIdAndClientId(Long companyId, Long clientId);

    ClientAccountDto getClientAccountInfo(Long companyId, Long clientAccountId);

    List<CliAccDtoForFront> getAllAcc(Long companyId);

    ClientAccountDto updateClientAccount(Long companyId,
                                         Long clientId,
                                         ClientAccountDto updatedClientAccount,
                                         Optional<Double> sum,
                                         Optional<String> action);

    Long[] getBonusSum(Long clientAccountId, Double buySum);
    ClientAccountDto getClientAccountById(Long clientId);

}
