package loyality.loyalityservice.controller;

import lombok.AllArgsConstructor;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.entity.Company;
import loyality.loyalityservice.service.ClientService;
import loyality.loyalityservice.service.CompanyService;
import loyality.loyalityservice.service.GroupService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import loyality.loyalityservice.dto.CompanyDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin("*")

@AllArgsConstructor
@RestController
@RequestMapping("/v1/company")

public class CompanyController {

    private CompanyService companyService;
    private ClientService clientService;
    private GroupService groupService;

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

    @GetMapping("/{id}/client-info/{client-id}")
    public ResponseEntity<ClientDto> getClientInfo(@PathVariable("id") Long companyId, @PathVariable("client-id") Long clientId){
        ClientDto client = clientService.getClientInfo(companyId, clientId);
        return ResponseEntity.ok(client);
    }


    //создание группы в компании
    @PostMapping("/{id}/new-group")
    public ResponseEntity<GroupDto> createGroup (@PathVariable("id") Long companyId, @RequestBody GroupDto groupDto){

        GroupDto savedGroup = groupService.createGroup(companyId, groupDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/all-groups")
    public ResponseEntity<List<GroupDto>> getAllGroups (@PathVariable("id") Long companyId){
        List<GroupDto> groups = groupService.getAllGroups(companyId);
        return ResponseEntity.ok(groups);
    }

    @PatchMapping("/{id}/group/{group-id}")
    public ResponseEntity<GroupDto> updateGroup (@PathVariable("id") Long companyId, @PathVariable("group-id") Long groupId, @RequestBody GroupDto groupDto){

        GroupDto savedGroup = groupService.updateGroup(companyId, groupId, groupDto);
        return new ResponseEntity<>(savedGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/group/{group-id}")
    public ResponseEntity<String> deleteGroup (@PathVariable("id") Long companyId, @PathVariable("group-id") Long groupId){
        groupService.deleteGroup(companyId, groupId);
        JSONObject response = new JSONObject();
        response.put("message", "Группа удалена!");
        return  ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

}
