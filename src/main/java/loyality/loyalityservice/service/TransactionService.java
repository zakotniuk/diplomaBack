package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.TransactionDto;

import java.util.List;
import java.util.Optional;


public interface TransactionService {

    TransactionDto createTransaction(Long companyId,
                                     Long clientId,
                                     ClientAccountDto clientDto,
                                     String action,
                                     Double sum);

    List<TransactionDto> getAllTransactions(Long companyId);

    List<TransactionDto> getTransactions(Long companyId, Optional<String> startDate, Optional<String> endDate);
}
