package com.mahov.passports.domain.mapper;


import com.mahov.dto.PassportDto;
import com.mahov.passports.domain.model.Passport;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {

  Passport toPassport(Long ownerId, PassportDto passportDto);

  Passport toPassport(PassportDto passportDto);

  PassportDto toPassportDto(Passport passport);

  List<PassportDto> toPassportDtos(List<Passport> passport);

  List<Passport> toPassports(List<PassportDto> passportDtos);
}
