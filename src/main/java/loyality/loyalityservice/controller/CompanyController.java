package loyality.loyalityservice.controller;

import lombok.AllArgsConstructor;

import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.dto.TransactionDto;
import loyality.loyalityservice.service.ClientAccountService;
import loyality.loyalityservice.service.CompanyService;
import loyality.loyalityservice.service.GroupService;
import loyality.loyalityservice.service.TransactionService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import loyality.loyalityservice.dto.CompanyDto;

import java.util.List;
import java.util.Optional;


@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/v1/company")

public class CompanyController {

    private CompanyService companyService;
    private ClientAccountService clientAccountService;
    private GroupService groupService;
    private TransactionService transactionService;


    ////////////////////КОМПАНИЯ//////////////////////
    @PostMapping()//ok
    //Создание компании
    public ResponseEntity<CompanyDto> createCompany (@RequestBody CompanyDto companyDto){
        CompanyDto savedCompany = companyService.createCompany(companyDto);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/info")//ok
    //получить информацию о компании
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("id") Long companyId){
        CompanyDto companyDto = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(companyDto);
    }



    ////////////////////СЧЕТА КЛИЕНТОВ//////////////////////
    @GetMapping("/{id}/all-client-accounts")
    //получить список всех счетов клиентов по компании
    public ResponseEntity<List<ClientAccountDto>> getAllClientAccounts(@PathVariable("id") Long companyId){
        List<ClientAccountDto> clients = clientAccountService.getAllClientAccounts(companyId);
        return ResponseEntity.ok(clients);
    }
    @PostMapping("/{id}/new-account")
    //создание счета клиента в компании
    public ResponseEntity<ClientAccountDto> createClientAccount(@PathVariable("id") Long companyId, @RequestBody ClientAccountDto clientAccountDto){
        ClientAccountDto savedClientAccount = clientAccountService.createClientAccount(companyId, clientAccountDto);
        return new ResponseEntity<>(savedClientAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/client-account-info/{client-id}")
    //получение информации о счете клиента
    //при передаче query параметра "sum" расчитывает суммы  к начислению и списанию
    public ResponseEntity<ClientAccountDto> getClientAccountInfo(
            @PathVariable("id") Long companyId,
            @PathVariable("client-id") Long clientId,
            @RequestParam("sum") Optional<Double> sum)
    {
        ClientAccountDto client = clientAccountService.getClientAccountInfo(companyId, clientId);

        //если в запросе передали сумму покупки,
        // то метод сразу рассчитывает сумму доступную к начислению и списанию и отдает их в ответе
        if (sum.isPresent()) {
            Long [] bonusSums = clientAccountService.getBonusSum(clientId, sum.orElseThrow());
            client.setBonusSumWriteOn(bonusSums[0]);
            client.setBonusSumWriteOff(bonusSums[1]);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
    @PatchMapping("/{id}/client-account-info/{client-id}")
    //изменение  счета клиента, в том числе и баланса
    public ResponseEntity<ClientAccountDto> updateClientAccount(
            @PathVariable("id") Long companyId,
            @PathVariable("client-id") Long clientId,
            @RequestBody ClientAccountDto clientDto,
            @RequestParam("sum") Optional<Double> sum,
            @RequestParam("action") Optional<String> action)
    {
        ClientAccountDto savedClient = clientAccountService.updateClientAccount(companyId, clientId, clientDto, sum, action);
//        JSONObject response = new JSONObject();
//        response.put(savedClient);
//        response.put("message", "Группа удалена!");
//        return  ResponseEntity.status(HttpStatus.OK).body(response.toString());
        return new ResponseEntity<ClientAccountDto>(savedClient, HttpStatus.OK);
    }




    ////////////////////СПИСОК ОПЕРАЦИЙ//////////////////////
    @GetMapping("/{id}/transactions")
    //получить список операций по компании
    public ResponseEntity<List<TransactionDto>> getTransactions(
            @PathVariable("id") Long companyId,
            @RequestParam("startDate") Optional<String> startDate,
            @RequestParam("endDate") Optional<String> endDate
    ){
        List<TransactionDto> transactions = transactionService.getTransactions(companyId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }


    ////////////////////ГРУППЫ//////////////////////
    @PostMapping("/{id}/new-group")
    //создание группы в компании
    public ResponseEntity<GroupDto> createGroup (@PathVariable("id") Long companyId, @RequestBody GroupDto groupDto){
        GroupDto savedGroup = groupService.createGroup(companyId, groupDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/all-groups")
    //получить список всех групп компании
    public ResponseEntity<List<GroupDto>> getAllGroups (@PathVariable("id") Long companyId){
        List<GroupDto> groups = groupService.getAllGroups(companyId);
        return ResponseEntity.ok(groups);
    }

    @PatchMapping("/{id}/group/{group-id}")
    //изменить группу в компаниии
    public ResponseEntity<GroupDto> updateGroup (@PathVariable("id") Long companyId, @PathVariable("group-id") Long groupId, @RequestBody GroupDto groupDto){
        GroupDto savedGroup = groupService.updateGroup(companyId, groupId, groupDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.OK);
    }
    @DeleteMapping("/{id}/group/{group-id}")
    //удалить группу в компании
    public ResponseEntity<String> deleteGroup (@PathVariable("id") Long companyId, @PathVariable("group-id") Long groupId){
        groupService.deleteGroup(companyId, groupId);
        JSONObject response = new JSONObject();
        response.put("message", "Группа удалена!");
        return  ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
}
