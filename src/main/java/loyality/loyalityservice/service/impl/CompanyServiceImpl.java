package loyality.loyalityservice.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;

import loyality.loyalityservice.dto.CompanyDto;
import loyality.loyalityservice.entity.Company;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.CompanyMapper;
import loyality.loyalityservice.mapper.EmployeeMapper;
import loyality.loyalityservice.qrcode.GenerateQrCode;
import loyality.loyalityservice.repository.CompanyRepository;
import loyality.loyalityservice.service.CompanyService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static loyality.loyalityservice.qrcode.GenerateQrCode.getQRCode;


@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto){
        Company company = CompanyMapper.mapToCompany(companyDto);


        //company.setQrLink("");
        Company savedCompany = companyRepository.save(company);

        // Генерация QR-кода
        try {
            //сюда в путь нужно указать публичный адрес на страницу добавления счета клиента в определенной компании
            String url = "http://loyalityService/company/" + savedCompany.getId(); // URL, который будет закодирован в QR-код
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            // Преобразование массива байтов в строку Base64
            String base64String = Base64.getEncoder().encodeToString(pngData);

            savedCompany.setQrLink(base64String); // обновляем QR-код в компании
            savedCompany = companyRepository.save(savedCompany); // сохраняем обновленную компанию
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }



        return CompanyMapper.mapToCompanyDto(savedCompany);
    }

    @Override
    public CompanyDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Company is not exist with given id: "+ companyId));
        company.setPassword("****");
        return CompanyMapper.mapToCompanyDto(company);

    }

    @Override
    public CompanyDto updateCompany(Long companyId, CompanyDto updatedCompany) {
        return null;
    }

    @Override
    public void deleteCompany(Long CompanyId) {

    }


}
