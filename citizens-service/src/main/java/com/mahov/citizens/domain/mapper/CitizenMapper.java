package com.mahov.citizens.domain.mapper;


import com.mahov.citizens.domain.model.Citizen;
import com.mahov.dto.request.CitizenCreateDto;
import com.mahov.dto.request.CitizenEditDto;
import com.mahov.dto.response.CitizenResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitizenMapper {

  Citizen toCitizen(CitizenCreateDto citizenCreateDto);

  Citizen toCitizen(CitizenEditDto citizenEditDto);

  CitizenResponseDto toCitizenResponseDto(Citizen citizen);
}
