package loyality.loyalityservice.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.TransactionDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.ClientAccount;
import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.ClientAccountMapper;
import loyality.loyalityservice.repository.*;
import loyality.loyalityservice.service.ClientAccountService;
import loyality.loyalityservice.service.TransactionService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService {

    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    private ClientAccountRepository clientAccountRepository;
    private GroupRepository groupRepository;
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;
    @Override
    //счет клиента создается в определенной компании!
    public ClientAccountDto createClientAccount(Long companyId, ClientAccountDto clientAccountDto) {
        ClientAccount clientAccount = ClientAccountMapper.mapToClientAccount(clientAccountDto);

        Long reqClientId = clientAccount.getClientId();
        Client client = clientRepository.findById(reqClientId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Указанного клиента не существует. ID="+ reqClientId));



        //проверка что в этой компании у клиента еще нет счета
        List<ClientAccount> cA = clientAccountRepository.findByCompanyIdAndClientId(companyId,reqClientId);
        if (Arrays.stream(cA.toArray()).count() > 0 ){
            throw new ResourceNotFoundException("У указанного  клиента уже существует счет в данной компании. ");
        }

        clientAccount.setCompanyId(companyId);
        clientAccount.setBalance(0L);
        clientAccount.setTotalMoneySpend(0L);

        //ищем дефолтную группу компании, которая должна присвоиться клиенту по умолчанию
        Long gId = groupRepository.getDefGroup(companyId);
        //задаем группу по умолчанию (в рамках конкретной компании)
        clientAccount.setGroupId(gId);

        ClientAccount savedClientAccount = clientAccountRepository.save(clientAccount);


        // Генерация QR-кода
        try {
            //сюда в путь нужно указать публичный адрес на страницу добавления счета клиента в определенной компании
            String url = "http://loyalityService/company/" + savedClientAccount.getId(); // URL, который будет закодирован в QR-код
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // Преобразование массива байтов в строку Base64
            String base64String = Base64.getEncoder().encodeToString(pngData);

            savedClientAccount.setQrLink(base64String); // обновляем QR-код в компании
            System.out.println(base64String);
            savedClientAccount = clientAccountRepository.save(savedClientAccount); // сохраняем обновленную компанию
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }


        return ClientAccountMapper.mapToClientAccountDto(savedClientAccount);
    }


    @Override
    //все счета по компании
    public List<ClientAccountDto> getAllClientAccounts(Long companyId) {
        List<ClientAccount> clientAccounts = clientAccountRepository.findByCompanyId(companyId);
        return clientAccounts.stream().map((clientAccount) -> ClientAccountMapper.mapToClientAccountDto(clientAccount))
                .collect(Collectors.toList());
    }

    @Override
    //все счета по клиенту
    public List<ClientAccountDto> getAllClientAccountsByClient(Long clientId) {
        List<ClientAccount> clientAccounts = clientAccountRepository.findByClientId(clientId);
        return clientAccounts.stream().map((clientAccount) -> ClientAccountMapper.mapToClientAccountDto(clientAccount))
                .collect(Collectors.toList());
    }


    @Override
    public ClientAccountDto getClientAccountInfo(Long companyId, Long clientAccountId) {
        ClientAccount clientAccount = clientAccountRepository.findById(clientAccountId)
                .orElseThrow(()->
                    new ResourceNotFoundException("Указанного клиента не существует. ID="+ clientAccountId));
        return ClientAccountMapper.mapToClientAccountDto(clientAccount);
    }

    @Override
    public ClientAccountDto updateClientAccount(Long companyId,
                                                Long clientAccountId,
                                                ClientAccountDto updatedClientAccount,
                                                Optional<Double> sum,
                                                Optional<String> action)
    {
        ClientAccount client = clientAccountRepository.findById(clientAccountId)
                .orElseThrow(() ->
                new ResourceNotFoundException("Указанного клиента не существует"+ clientAccountId));

        if(client.getCompanyId() != null && !client.getCompanyId().equals(companyId)){
            throw new ResourceNotFoundException("Некорректный запрос. Указанного клиента нет в компании!");
        }

        //класс для обновления полей в объекте
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Проверка, что клиенту не укажут другую компанию//
        if(updatedClientAccount.getCompanyId() != null && updatedClientAccount.getCompanyId() != client.getCompanyId() ){
            throw new ResourceNotFoundException("Нельзя присвоить клиента чужой компании!");
        }

        TransactionDto tr = new TransactionDto();

        if (sum.isPresent() && action.get().equals("WRITEON")){
            //передана сумма покупки и флаг "Начислить"
            Long [] bonusSums = new Long[2];
            bonusSums = this.getBonusSum(clientAccountId, sum.get());
            if (bonusSums[0] > 0) {
                updatedClientAccount.setBalance(client.getBalance() + bonusSums[0]);
                tr =  transactionService.createTransaction(companyId, clientAccountId, updatedClientAccount, action.get(), bonusSums[0].doubleValue());

            }else{
                System.out.println("Начислить ничего нельзя :)");
            }

        } else if (sum.isPresent() && action.get().equals("WRITEOFF")){
            //передана сумма покупки и флаг "Списать"
            Long [] bonusSums = new Long[2];
            bonusSums = this.getBonusSum(clientAccountId, sum.get());

            if (bonusSums[1] > 0){
                updatedClientAccount.setBalance(client.getBalance() - bonusSums[1]);
                tr =  transactionService.createTransaction(companyId, clientAccountId, updatedClientAccount, action.get(), bonusSums[1].doubleValue());
                client.setTotalMoneySpend(client.getTotalMoneySpend()+bonusSums[1]);

                // менять группу если выполнено условие перехода в новую группу
                Long actualGroup = groupRepository.getActualGroup(companyId, client.getTotalMoneySpend());
                if (client.getGroupId() != actualGroup && actualGroup != null) {
                    client.setGroupId(actualGroup);
                    System.out.println("Группа клиента изменена: " + actualGroup);
                }

            }else{
                System.out.println("Списать ничего нельзя :)");
            }
        }

   //     if (tr != null) System.out.println(tr.getTransactionId());

        //записываем в объект только измененные поля
        modelMapper.map(updatedClientAccount, client);
        //сохраняем объект
        ClientAccount updatedClientObj = clientAccountRepository.save(client);
        return ClientAccountMapper.mapToClientAccountDto(updatedClientObj);
    }



    @Override
    public Long[] getBonusSum(Long clientAccountId, Double buySum) {
        //функция расчета доступного кол-ва бонусов для начисления и списания

        Long balance, //текущий баланс клиента
                bonusSumWriteOn = 0L, //доступно к начислению
                bonusSumWriteOff = 0L; //доступно к списанию
        Integer discount,//сумма бонусов в процентах % от покупки'
                availablePart;//часть, которую можно покрыть баллами (0-100%)

        ClientAccount clientAccount = clientAccountRepository.findById(clientAccountId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Указанного клиента не существует. ID="+ clientAccountId));
        //получаем баланс клиента
        balance = clientAccount.getBalance();
        //получаем группу клиента
        Group group = groupRepository.findById(clientAccount.getGroupId()).orElseThrow(()->
                new RuntimeException("Ошибка системы, не нашлась группа клиента."));
        discount = group.getDiscount();
        availablePart = group.getAvailablePart();

        //на основе группы и баланса считаем две суммы:

        // 1. доступная к начислению
        bonusSumWriteOn = (long) ((buySum / 100) * discount);

        // 2. доступная к списанию
        long max =  (long) ((buySum/100)*availablePart);
        if (balance >= max)
        {
            bonusSumWriteOff = max;
        } else{
            bonusSumWriteOff = balance;
        }

        Long[] bonusSums; // объявление массива для хранения результатов
        bonusSums = new Long[2];
        bonusSums[0]=bonusSumWriteOn;
        bonusSums[1]=bonusSumWriteOff;

        return bonusSums;
    }

    @Override
    public ClientAccountDto getClientAccountById(Long clientId) {
        return null;
    }
}
