package loyality.loyalityservice.mapper;

import loyality.loyalityservice.dto.CompanyDto;
import loyality.loyalityservice.entity.Company;

public class CompanyMapper {
    //класс для сопоставления сущности c DTO объектом

    public static CompanyDto mapToCompanyDto(Company company){
        return new CompanyDto(
                company.getId(),
                company.getCompanyName(),
                company.getEmail(),
                company.getPassword(),
                company.getPhone(),
                company.getDescription(),
                company.getQrLink()
        );
    }

    public static Company mapToCompany (CompanyDto companyDto){
        return new Company(
                companyDto.getId(),
                companyDto.getCompanyName(),
                companyDto.getEmail(),
                companyDto.getPassword(),
                companyDto.getPhone(),
                companyDto.getDescription(),
                companyDto.getQrLink()
        );
    }
}
