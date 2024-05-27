package loyality.loyalityservice.service;

import loyality.loyalityservice.dto.CompanyDto;

public interface CompanyService {

    CompanyDto createCompany (CompanyDto companyDto);
    CompanyDto getCompanyById (Long companyId);
    CompanyDto updateCompany(Long companyId, CompanyDto updatedCompany);
    void deleteCompany (Long CompanyId);


}

