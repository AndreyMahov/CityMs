package com.mahov.citizens.service.impl;

import com.mahov.citizens.domain.enums.Gender;
import com.mahov.citizens.domain.model.Citizen;
import com.mahov.citizens.repository.CitizenRepository;
import com.mahov.citizens.service.CitizenService;
import com.mahov.citizens.service.RegistrationService;
import com.mahov.citizens.service.feign.OcContinuousLogServiceFeignClient;
import com.mahov.citizens.service.feign.PassportServerFeignClient;
import com.mahov.dto.PassportDto;
import com.mahov.dto.request.LogCreateRequestDto;
import com.mahov.enums.ModelName;
import com.mahov.enums.PlaceWork;
import com.mahov.enums.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
   static Integer countAdding = 10;
   CitizenRepository citizenRepository;
   PassportServerFeignClient passportServerFeignClient;
   CitizenService citizenService;
   Random random;
   OcContinuousLogServiceFeignClient ocContinuousLogServiceFeignClient;

  @Override
  @Scheduled(fixedDelay = 1000*10)
  @Async
  public void addCitizensByTimer() {
    List<Citizen> newCitizen = new ArrayList<>();
     for(int i = 0; i < countAdding; i++) {
       newCitizen.add(Citizen
          .builder()
          .name("name")
          .surname("surname")
          .gender(Gender.MALE)
          .status(Status.ACTIVE)
          .placeWork(i%2==0 ? PlaceWork.FACTORY:PlaceWork.ANOTHER)
          .build());
    }
    if (isCountInDBNoEqualsCountInLog()){
      throw new RuntimeException();
    }
    var citizens = citizenRepository.saveAll(newCitizen);
    createPassport(citizens);
    sendCreateLogToOcService(citizens);
  }

  @Synchronized
  private boolean isCountInDBNoEqualsCountInLog(){
    Integer countInDb = citizenService.getLogCount();
    Integer countInLog = ocContinuousLogServiceFeignClient.getCitizensCountFromOcService().getCount();
    return !(countInLog.equals(countInDb));
  }

  private void sendCreateLogToOcService(List<Citizen> citizens){
    citizenService.addToLogCount(citizens.size());
    ocContinuousLogServiceFeignClient.create(LogCreateRequestDto
        .builder()
        .modelName(ModelName.CITIZENS)
        .count(citizenService.getLogCount())
        .build());
  }

  private void createPassport(List<Citizen> citizens){
    List<PassportDto> passports = citizens
        .stream()
        .map(citizen -> new PassportDto(citizen.getId(),createPassportNumber()))
        .toList();
    passportServerFeignClient.create(passports);
  }

  private Integer createPassportNumber(){
    return random.nextInt(900000) + 100000;
  }
}
