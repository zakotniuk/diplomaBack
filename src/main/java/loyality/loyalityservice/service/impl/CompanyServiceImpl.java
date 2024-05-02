package loyality.loyalityservice.service.impl;

import com.google.zxing.WriterException;
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

import java.io.IOException;
import java.util.List;

import static loyality.loyalityservice.qrcode.GenerateQrCode.getQRCode;


@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto){
        Company company = CompanyMapper.mapToCompany(companyDto);
        Company savedCompany = companyRepository.save(company);
        //
//        GenerateQrCode qr = new GenerateQrCode();
//        byte[] image = new byte[0];
//            // Generate and Return Qr Code in Byte Array
//        try {
//            image = GenerateQrCode.getQRCode();
//        }catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//
        //привет
        //привет2
        return CompanyMapper.mapToCompanyDto(savedCompany);
    }

    @Override
    public CompanyDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Company is not exist with given id: "+ companyId));
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
