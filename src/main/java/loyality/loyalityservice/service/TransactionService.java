package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TransactionService {

    TransactionDto createTransaction(Long companyId,
                                     Long clientId,
                                     ClientDto clientDto,
                                     String action,
                                     Double sum);

    List<TransactionDto> getAllTransactions(Long companyId);
}
