package com.mahov.cars.domain.mapper;


import com.mahov.cars.domain.model.Car;
import com.mahov.dto.request.CarRequestDto;
import com.mahov.dto.response.CarResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

  Car toCar(CarRequestDto carRequestDto);

  CarResponseDto toCarResponseDto(Car car);
}
