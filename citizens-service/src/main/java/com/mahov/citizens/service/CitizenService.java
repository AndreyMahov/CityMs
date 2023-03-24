package com.mahov.citizens.service;

import com.mahov.citizens.domain.model.Citizen;
import com.mahov.dto.PassportDto;
import com.mahov.dto.response.CarResponseDto;
import java.util.List;

public interface CitizenService {

  List<Citizen> getAll();

  Citizen getById(Long id);

  Citizen create(Citizen citizen, PassportDto passportDto);

  Citizen edit(Long id, Citizen citizen);

  void delete(Long id);

  List<CarResponseDto> getCarsById(Long id);

  List<PassportDto> getPassportByFirstLetterInLastName(String firstLetter);

  void rollbackDeletedById(Long citizenId);

  Integer incrementLogCount();

  Integer addToLogCount(Integer integer);

  Integer getLogCount();

  void sendLogByTimer();
}
