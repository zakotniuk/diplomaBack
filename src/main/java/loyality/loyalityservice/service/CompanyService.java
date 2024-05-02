package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.ClientDto;
import loyality.loyalityservice.dto.CompanyDto;
import loyality.loyalityservice.dto.EmployeeDto;
import loyality.loyalityservice.entity.Client;

import java.util.List;

public interface CompanyService {

    CompanyDto createCompany (CompanyDto companyDto);
    CompanyDto getCompanyById (Long companyId);
    CompanyDto updateCompany(Long companyId, CompanyDto updatedCompany);
    void deleteCompany (Long CompanyId);


}

