package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.TransactionDto;
import loyality.loyalityservice.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
              //  transaction.getTransactionId(),
                transaction.getCompanyId(),
                transaction.getClientAccountId(),
                transaction.getAction(),
                transaction.getSum(),
                transaction.getCreateDate()
        );
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto){
        return new Transaction(
                transactionDto.getId(),
            //    transactionDto.getTransactionId(),
                transactionDto.getCompanyId(),
                transactionDto.getClientAccountId(),
                transactionDto.getAction(),
                transactionDto.getSum(),
                transactionDto.getCreateDate()
        );
    }
}
