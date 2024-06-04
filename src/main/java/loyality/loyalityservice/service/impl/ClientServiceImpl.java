package loyality.loyalityservice.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.entity.Client;
import loyality.loyalityservice.entity.ClientAccount;
import loyality.loyalityservice.entity.Company;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.ClientAccountMapper;
import loyality.loyalityservice.mapper.ClientMapper;
import loyality.loyalityservice.mapper.CompanyMapper;
import loyality.loyalityservice.repository.ClientRepository;
import loyality.loyalityservice.service.ClientService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;



    @Override
    public ClientDto newClient(ClientDto clientDto) {
        Client client = ClientMapper.mapToClient(clientDto);
        Client savedClient = clientRepository.save(client);


        return ClientMapper.mapToClientDto(savedClient);
    }

    @Override
    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Client is not exist with given id: "+ clientId));
        client.setPassword("****");
        return ClientMapper.mapToClientDto(client);
    }
}
