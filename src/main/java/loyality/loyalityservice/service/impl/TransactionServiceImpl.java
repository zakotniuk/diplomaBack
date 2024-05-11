package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.TransactionDto;
import loyality.loyalityservice.entity.Transaction;
import loyality.loyalityservice.mapper.TransactionMapper;
import loyality.loyalityservice.repository.TransactionRepository;
import loyality.loyalityservice.service.TransactionService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    @Override
    public TransactionDto createTransaction(Long companyId,
                                            Long clientId,
                                            ClientDto clientDto,
                                            String action,
                                            Double sum) {

        //LocalDateTime currentDateTime = LocalDateTime.now();
        //ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        Date createDate = clientDto.getUpdateDate();
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd-ss");

        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId(clientId, dt1.format(createDate).toString()));
        transaction.setCompanyId(companyId);
        transaction.setClientId(clientId);
        transaction.setAction(action);
        transaction.setSum(sum);
        transaction.setCreateDate(createDate.toString());


        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    public String generateTransactionId(Long clientId, String date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String transactionId = date + "_" + clientId.toString();

        return transactionId;
    }



    @Override
    public List<TransactionDto> getAllTransactions(Long companyId) {
        return null;
    }
}
