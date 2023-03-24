package com.mahov.houses.controller.impl;

import com.mahov.dto.response.HouseResponseDto;
import com.mahov.houses.controller.HouseController;
import com.mahov.houses.domain.dto.request.HouseCreateDto;
import com.mahov.houses.domain.mapper.HouseMapper;
import com.mahov.houses.service.HouseService;
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
@RequestMapping("${url.houses-service}")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HouseControllerImpl implements HouseController {
  HouseService houseService;
  HouseMapper houseMapper;

  @GetMapping
  public List<HouseResponseDto> getAll() {
    return houseService.getAll()
        .stream()
        .map(houseMapper::toHouseResponseDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public HouseResponseDto getById(@PathVariable("id") Long id) {
    return houseMapper.toHouseResponseDto(houseService.getById(id));
  }

  @PostMapping
  public HouseResponseDto create(@RequestBody HouseCreateDto houseCreateDto) {
    return houseMapper.toHouseResponseDto(houseService.create(houseMapper.toHouse(houseCreateDto)));
  }

  @PatchMapping("/{id}")
  public HouseResponseDto edit(@PathVariable("id") Long id,
      @RequestBody HouseCreateDto houseCreateDto) {
    return houseMapper.toHouseResponseDto(
        houseService.edit(id, houseMapper.toHouse(houseCreateDto)));
  }

  @GetMapping("/owners")
  public List<Long> getAllOwner(@RequestParam String street) {
    return houseService.getOwners(street);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Long id) {
    houseService.delete(id);
  }

  @DeleteMapping("/owners/{ownerId}")
  public void deleteOwnerById(@PathVariable("ownerId") Long ownerId) {
    houseService.deleteOwnerById(ownerId);
  }
}

