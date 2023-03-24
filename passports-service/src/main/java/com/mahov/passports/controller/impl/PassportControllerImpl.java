package com.mahov.passports.controller.impl;


import com.mahov.dto.PassportDto;
import com.mahov.passports.controller.PassportController;
import com.mahov.passports.domain.mapper.PassportMapper;
import com.mahov.passports.service.PassportService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url.passports-service}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PassportControllerImpl implements PassportController {
  PassportService passportService;
  PassportMapper passportMapper;

  @GetMapping
  public List<PassportDto> getAll() {
    return passportService.getAllActivePassportsById()
        .stream()
        .map(passportMapper::toPassportDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public Object getByid(@PathVariable("id") Long id) {
    return passportMapper.toPassportDto(passportService.getActivePassportById(id));
  }

  @GetMapping("/owners")
  public List<PassportDto> getAllPassportByOwnersIds(@RequestBody List<Long> ownersIds) {
    return passportService.getAllPassportByOwnersIds(ownersIds)
        .stream()
        .map(passportMapper::toPassportDto)
        .collect(Collectors.toList());
  }

  @PostMapping("/{ownerId}")
  public PassportDto create(@PathVariable Long ownerId, @RequestBody PassportDto passportDto) {
    return passportMapper.toPassportDto(
        passportService.create(ownerId, passportMapper.toPassport(passportDto)));
  }

  @PostMapping
  public void create(@RequestBody List<PassportDto> passportDtos){
    passportService.creates(passportMapper.toPassports(passportDtos));
  }

  @DeleteMapping("/{ownerId}")
  public void deleteByOwnerId(@PathVariable("ownerId") Long ownerId) {
    passportService.deleteByOwnerId(ownerId);
  }
}