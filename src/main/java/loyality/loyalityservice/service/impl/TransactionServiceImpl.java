package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.TransactionDto;
import loyality.loyalityservice.entity.Transaction;
import loyality.loyalityservice.mapper.TransactionMapper;
import loyality.loyalityservice.repository.TransactionRepository;
import loyality.loyalityservice.service.TransactionService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    @Override
    public TransactionDto createTransaction(Long companyId,
                                            Long clientAccountId,
                                            ClientAccountDto clientDto,
                                            String action,
                                            Double sum) {

        Date createDate = clientDto.getUpdateDate();
        //System.out.println(createDate);

        Transaction transaction = new Transaction();

        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd-ss");
        //transaction.setTransactionId(generateTransactionId(clientId, dt1.format(createDate).toString()));

        transaction.setCompanyId(companyId);
        transaction.setClientAccountId(clientAccountId);
        transaction.setAction(action);
        transaction.setSum(sum);

        SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        transaction.setCreateDate(dt2.format(createDate).toString());

        Transaction savedTransaction = transactionRepository.save(transaction);

        //drop old transactions (в будущем вынести в отдельную джобу)
        LocalDate currentDate = LocalDate.now();
        // Вычитаем 15 дней
        LocalDate dateX = currentDate.minusDays(15);
        try {
            //transactionRepository.dropTransactionsByDateX(dateX.toString());
            //System.out.println("Таблица транзакций успешно очищена, остались записи за последние 14 дней.");
        } catch (Exception e){System.out.println("Очистка всех операций, кроме последних 14 дней не выполнена.");}

        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    public String generateTransactionId(Long clientId, String date) {
        return date.replace("-", "") + clientId.toString();
    }



    @Override
    public List<TransactionDto> getAllTransactions(Long companyId) {
        return null;
    }

    @Override
    public List<TransactionDto> getTransactions(Long companyId, Optional<String> startDate, Optional<String> endDate) {

        List<Transaction> transactions = new ArrayList<>();

        if (!startDate.isPresent()) {
            transactions = transactionRepository.getLimitedCountTransactions(companyId, 100);
        }
        if (startDate.isPresent() && endDate.isPresent()){
            transactions = transactionRepository.getTransactionsByPeriod(companyId,startDate.get(),endDate.get());
        }

        return transactions.stream().map((transaction) -> TransactionMapper.mapToTransactionDto(transaction))
                .collect(Collectors.toList());

    }
}
