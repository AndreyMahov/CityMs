package com.mahov.cars.controller.impl;

import com.mahov.cars.controller.CarController;
import com.mahov.cars.domain.mapper.CarMapper;
import com.mahov.cars.service.CarService;
import com.mahov.dto.request.CarRequestDto;
import com.mahov.dto.response.CarResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${url.cars-service}")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CarControllerImpl implements CarController {
  CarService carService;
  CarMapper carMapper;

  @GetMapping
  public List<CarResponseDto> getAll() {
    return carService.getAllActive()
        .stream()
        .map(carMapper::toCarResponseDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public CarResponseDto getByid(@PathVariable("id") Long id) {
    return carMapper.toCarResponseDto(carService.getActiveByid(id));
  }

  @PostMapping
  public CarResponseDto create(@RequestBody CarRequestDto carRequestDto) {
    return carMapper.toCarResponseDto(carService.create(carMapper.toCar(carRequestDto)));
  }

  @PatchMapping("/{id}")
  public CarResponseDto edit(@PathVariable("id") Long id, @RequestBody CarRequestDto car) {
    return carMapper.toCarResponseDto(carService.edit(id, carMapper.toCar(car)));
  }

  @DeleteMapping("/owners/{ownerId}")
  public void deleteByOwnerId(@PathVariable("ownerId") Long id) {
    carService.deleteByOwnerId(id);
  }

  @GetMapping("owners/{ownerId}")
  public List<CarResponseDto> getAllByOwnerId(@PathVariable("ownerId") Long ownerId) {
    return carService.getAllByOwnerId(ownerId)
        .stream()
        .map(carMapper::toCarResponseDto)
        .collect(Collectors.toList());
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    carService.delete(id);
  }
}

