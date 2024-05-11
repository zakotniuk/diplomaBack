package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TransactionService {

    TransactionDto createTransaction(Long companyId,
                                     Long clientId,
                                     ClientDto clientDto,
                                     String action,
                                     Double sum);

    List<TransactionDto> getAllTransactions(Long companyId);

    List<TransactionDto> getTransactions(Long companyId, Optional<String> startDate, Optional<String> endDate);
}
