package loyality.loyalityservice.controller;


import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.CompanyDto;
import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.service.ClientAccountService;
import loyality.loyalityservice.service.ClientService;
import loyality.loyalityservice.service.CompanyService;
import loyality.loyalityservice.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/v1/client")

public class ClientController {


    private ClientService clientService;
    private ClientAccountService clientAccountService;
    private GroupService groupService;


    @PostMapping("/create")
    public ResponseEntity<ClientDto> newClient (@RequestBody ClientDto clientDto){
        ClientDto savedClient = clientService.newClient(clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<ClientDto> getClientById (@PathVariable("id") Long clientId){
        ClientDto savedClientDto = clientService.getClientById(clientId);
        return ResponseEntity.ok(savedClientDto);
    }


//    @GetMapping("/{id}/accounts")
//    public ResponseEntity<List<ClientAccountDto>> getAllClientAccountsByClient(@PathVariable("id") Long clientId){
//        List<ClientAccountDto> clients = clientAccountService.getAllClientAccountsByClient(clientId);
//        //GroupDto groupName = groupService.getGroupByClient(clientId);
//
//        return ResponseEntity.ok(clients);
//    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<ClientAccountDto>> getAllClientAccountsByClient(@PathVariable("id") Long clientId){
        List<ClientAccountDto> clients = clientAccountService.getAllClientAccountsByClient(clientId);
        return ResponseEntity.ok(clients);
    }


//    //получить информацию о клиенте
//    @GetMapping("/{id}/info")
//    public ResponseEntity<ClientAccountDto> getClientById(@PathVariable("id") Long clientId){
//        ClientAccountDto clientDto = clientService.getClientAccountById(clientId);
//        return ResponseEntity.ok(clientDto);
//    }

}
