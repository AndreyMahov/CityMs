package com.mahov.citizens.controller.impl;

import com.mahov.citizens.controller.CitizenController;
import com.mahov.citizens.domain.mapper.CitizenMapper;
import com.mahov.citizens.service.CitizenService;
import com.mahov.dto.PassportDto;
import com.mahov.dto.request.CitizenCreateDto;
import com.mahov.dto.request.CitizenEditDto;
import com.mahov.dto.response.CarResponseDto;
import com.mahov.dto.response.CitizenResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${url.citizen-service}")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenControllerImpl implements CitizenController {
   CitizenService citizenService;
   CitizenMapper citizenMapper;

  @GetMapping
  public List<CitizenResponseDto> getAll() {
    return citizenService.getAll()
        .stream()
        .map(citizenMapper::toCitizenResponseDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public CitizenResponseDto getById(@PathVariable("id") Long id) {
    return citizenMapper.toCitizenResponseDto(citizenService.getById(id));
  }

  @PostMapping
  public CitizenResponseDto create(@RequestBody CitizenCreateDto citizen) {
    return citizenMapper.toCitizenResponseDto(
        citizenService.create(citizenMapper.toCitizen(citizen), citizen.getPassportDto()));
  }

  @PatchMapping("/{id}")
  public CitizenResponseDto edit(@PathVariable("id") Long id,
      @RequestBody CitizenEditDto citizenEditDto) {
    return citizenMapper.toCitizenResponseDto(
        citizenService.edit(id, citizenMapper.toCitizen(citizenEditDto)));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    citizenService.delete(id);
  }

  @GetMapping("/{ownerId}/cars")
  public List<CarResponseDto> getCitizenCars(@PathVariable("ownerId") Long ownerId) {
    return citizenService.getCarsById(ownerId);
  }

  @GetMapping("/passports")
  public List<PassportDto> getPassportByFirstLetterInLastName(@RequestParam String firstLetter) {
    return citizenService.getPassportByFirstLetterInLastName(firstLetter);
  }
}
