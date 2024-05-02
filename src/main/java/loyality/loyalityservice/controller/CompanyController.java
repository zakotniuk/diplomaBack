package loyality.loyalityservice.controller;

import lombok.AllArgsConstructor;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.service.ClientService;
import loyality.loyalityservice.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import loyality.loyalityservice.dto.CompanyDto;

import java.util.List;


@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/v1/company")

public class CompanyController {

    private CompanyService companyService;
    private ClientService clientService;

    @PostMapping()
    public ResponseEntity<CompanyDto> createCompany (@RequestBody CompanyDto companyDto){
        CompanyDto savedCompany = companyService.createCompany(companyDto);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    //получить информацию о компании
    @GetMapping("/{id}/info")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("id") Long companyId){
        CompanyDto companyDto = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(companyDto);
    }

    //получить список всех клиентов по компании
    @GetMapping("/{id}/all-clients")
    public ResponseEntity<List<ClientDto>> getAllClients(@PathVariable("id") Long companyId){
        List<ClientDto> clients = clientService.getAllClients(companyId);
        return ResponseEntity.ok(clients);
    }

    //создание клиента в компании
    @PostMapping("/{id}/new-client")
    public ResponseEntity<ClientDto> createClient (@PathVariable("id") Long companyId, @RequestBody ClientDto clientDto){
        ClientDto savedClient = clientService.createClient(companyId, clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
}
