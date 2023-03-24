package com.mahov.passports.service.impl;


import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import com.mahov.enums.ExceptionMessage;
import com.mahov.enums.ModelName;
import com.mahov.enums.Status;
import com.mahov.exception.NotFoundException;
import com.mahov.log.kafka.service.LogService;
import com.mahov.passports.domain.model.Passport;
import com.mahov.passports.kafka.CitizenRollbackDeleteEventProducer;
import com.mahov.passports.repository.PassportRepository;
import com.mahov.passports.service.PassportService;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PassportServiceImpl implements PassportService {
  PassportRepository passportRepository;
  CitizenRollbackDeleteEventProducer citizenRollbackDeleteEventProducer;
  LogService logService;
  AtomicInteger entityCount;

  @Override
  public Passport create(Long ownerId, Passport passport) {
    log.info("PassportService method create started working");
    passport.setStatus(Status.ACTIVE);
    passport.setOwnerId(ownerId);
    var result = passportRepository.save(passport);
    logService.sendContinuousLogToOCService(entityCount.addAndGet(1), ModelName.PASSPORT);
    return result;
  }

  @Override
  public List<Passport> getAllActivePassportsById() {
    return passportRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public Passport getActivePassportById(Long id) {
    return passportRepository.findByIdAndStatus(id,Status.ACTIVE)
        .orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND));
  }

  @Override
  public void deleteByOwnerId(Long ownerId) {
    try {
      int countDeletedPassports = 1;
      boolean isDeletedFail = passportRepository.setDeleteStatusByOwnerId(ownerId) != countDeletedPassports;
      if (isDeletedFail){
        throw new NotFoundException(ExceptionMessage.NOT_FOUND);
      }
      logService.sendContinuousLogToOCService(entityCount.addAndGet(-1),ModelName.PASSPORT);
    } catch (Exception e) {
      citizenRollbackDeleteEventProducer.sendMessage(new CitizenRollBackDeleteEventMessage(ownerId));
    }
  }

  @Override
  public List<Passport> getAllPassportByOwnersIds(List<Long> ownersIds) {
    return ownersIds.stream()
        .map(passportRepository::findByOwnerId)
        .collect(Collectors.toList());
  }

  @Override
  public void creates(List<Passport> passportDtos) {
    logService.sendContinuousLogToOCService(
        entityCount.addAndGet(passportRepository.saveAll(passportDtos).size()),ModelName.PASSPORT);
  }

  @Override
  @Scheduled(fixedDelay = 1000*60*5)
  public void sendLogByTimer(){
    logService.sendLogByTimerToOCService(entityCount.get(),ModelName.PASSPORT);
  }
}
