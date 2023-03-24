package com.mahov.houses.domain.mapper;


import com.mahov.dto.response.HouseResponseDto;
import com.mahov.houses.domain.dto.request.HouseCreateDto;
import com.mahov.houses.domain.model.House;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HouseMapper {

  House toHouse(HouseCreateDto houseCreateDto);

  HouseResponseDto toHouseResponseDto(House house);
}
