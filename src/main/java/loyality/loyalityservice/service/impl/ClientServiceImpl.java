package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.entity.Transaction;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.ClientMapper;
import loyality.loyalityservice.mapper.GroupMapper;
import loyality.loyalityservice.repository.ClientRepository;
import loyality.loyalityservice.repository.CompanyRepository;
import loyality.loyalityservice.repository.GroupRepository;
import loyality.loyalityservice.repository.TransactionRepository;
import loyality.loyalityservice.service.ClientService;
import loyality.loyalityservice.service.TransactionService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    private GroupRepository groupRepository;
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;
    @Override
    //клиент создается в определенной компании!
    public ClientDto createClient(Long companyId, ClientDto clientDto) {

        Client client = ClientMapper.mapToClient(clientDto);
        //SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        //Date date = new Date(String.valueOf(client.getBirthDate()));
        client.setBirthDate(client.getBirthDate());
        client.setCompanyId(companyId);
        client.setBalance(0L);
        client.setTotalMoneySpend(0L);

        //ищем дефолтную группу компании, которая должна присвоиться клиенту по умолчанию
        Long gId = groupRepository.getDefGroup(companyId);
        //задаем группу по умолчанию (в рамках конкретной компании)
        client.setGroupId(gId);

        Client savedClient = clientRepository.save(client);
        return ClientMapper.mapToClientDto(savedClient);
    }



    @Override
    //клиенты ищутся по определенной компании!
    public List<ClientDto> getAllClients(Long companyId) {
        List<Client> clients = clientRepository.findByCompanyId(companyId);
        return clients.stream().map((client) -> ClientMapper.mapToClientDto(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientInfo(Long companyId, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->
                    new ResourceNotFoundException("Указанного клиента не существует. ID="+ clientId));
        return ClientMapper.mapToClientDto(client);
    }

    @Override
    public ClientDto updateClient(Long companyId,
                                  Long clientId,
                                  ClientDto updatedClient,
                                  Optional<Double> sum,
                                  Optional<String> action)
    {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() ->
                new ResourceNotFoundException("Указанного клиента не существует"+ clientId));

        if(client.getCompanyId() != null && !client.getCompanyId().equals(companyId)){
            throw new ResourceNotFoundException("Некорректный запрос. Указанного клиента нет в компании!");
        }

        //класс для обновления полей в объекте
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //Проверка, что клиенту не укажут другую компанию//
        if(updatedClient.getCompanyId() != null && updatedClient.getCompanyId() != client.getCompanyId() ){
            throw new ResourceNotFoundException("Нельзя присвоить клиента чужой компании!");
        }

        if (sum.isPresent() && action.get().equals("WRITEON")){
            //передана сумма покупки и флаг "Начислить"
            Long [] bonusSums = new Long[2];
            bonusSums = this.getBonusSum(clientId, sum.get());
            if (bonusSums[0] > 0) {
                updatedClient.setBalance(client.getBalance() + bonusSums[0]);
                transactionService.createTransaction(companyId, clientId, updatedClient, action.get(), bonusSums[0].doubleValue());
            }else{
                System.out.println("Начислить ничего нельзя :)");
            }

        } else if (sum.isPresent() && action.get().equals("WRITEOFF")){
            //передана сумма покупки и флаг "Списать"
            Long [] bonusSums = new Long[2];
            bonusSums = this.getBonusSum(clientId, sum.get());

            if (bonusSums[1] > 0){
                updatedClient.setBalance(client.getBalance() - bonusSums[1]);
                transactionService.createTransaction(companyId, clientId, updatedClient, action.get(), bonusSums[1].doubleValue());
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


        //записываем в объект только измененные поля
        modelMapper.map(updatedClient, client);
        //сохраняем объект
        Client updatedClientObj = clientRepository.save(client);
        return ClientMapper.mapToClientDto(updatedClientObj);
    }



    @Override
    public Long[] getBonusSum(Long clientId, Double buySum) {
        //функция расчета доступного кол-ва бонусов для начисления и списания

        Long balance, //текущий баланс клиента
                bonusSumWriteOn = 0L, //доступно к начислению
                bonusSumWriteOff = 0L; //доступно к списанию
        Integer discount,//сумма бонусов в процентах % от покупки'
                availablePart;//часть, которую можно покрыть баллами (0-100%)

        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Указанного клиента не существует. ID="+ clientId));
        //получаем баланс клиента
        balance = client.getBalance();
        //получаем группу клиента
        Group group = groupRepository.findById(client.getGroupId()).orElseThrow(()->
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
}
