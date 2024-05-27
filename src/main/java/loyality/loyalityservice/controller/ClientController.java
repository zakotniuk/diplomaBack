package loyality.loyalityservice.controller;


import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientAccountDto;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.service.ClientAccountService;
import loyality.loyalityservice.service.ClientService;
import loyality.loyalityservice.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/v1/client")

public class ClientController {


    private ClientService clientService;


    @PostMapping("/create")
    public ResponseEntity<ClientDto> newClient (@RequestBody ClientDto clientDto){
        ClientDto savedClient = clientService.newClient(clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

//    //получить информацию о клиенте
//    @GetMapping("/{id}/info")
//    public ResponseEntity<ClientAccountDto> getClientById(@PathVariable("id") Long clientId){
//        ClientAccountDto clientDto = clientService.getClientAccountById(clientId);
//        return ResponseEntity.ok(clientDto);
//    }

}
