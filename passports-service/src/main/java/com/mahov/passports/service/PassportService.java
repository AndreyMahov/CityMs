package com.mahov.passports.service;


import com.mahov.passports.domain.model.Passport;
import java.util.List;

public interface PassportService {

  Passport create(Long CitizenId, Passport passportDto);

  List<Passport> getAllActivePassportsById();

  Passport getActivePassportById(Long id);

  void deleteByOwnerId(Long id);

  List<Passport> getAllPassportByOwnersIds(List<Long> ownersIds);

  void creates(List<Passport> passportDtos);

  void sendLogByTimer();
}
