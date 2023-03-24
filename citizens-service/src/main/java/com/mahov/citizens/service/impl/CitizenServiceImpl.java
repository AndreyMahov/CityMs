package com.mahov.citizens.service.impl;


import com.mahov.citizens.domain.model.Citizen;
import com.mahov.citizens.kafka.event.delete.DeleteEventProducer;
import com.mahov.citizens.repository.CitizenRepository;
import com.mahov.citizens.service.CitizenService;
import com.mahov.citizens.service.feign.CarServerFeignClient;
import com.mahov.citizens.service.feign.PassportServerFeignClient;
import com.mahov.dto.PassportDto;
import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.response.CarResponseDto;
import com.mahov.enums.ExceptionMessage;
import com.mahov.enums.ModelName;
import com.mahov.enums.Status;
import com.mahov.exception.NotFoundException;
import com.mahov.log.kafka.service.LogService;
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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {
  CitizenRepository citizenRepository;
  PassportServerFeignClient passportServerFeignClient;
  CarServerFeignClient carServerFeignClient;
  DeleteEventProducer deleteEventProducer;
  LogService logService;
  AtomicInteger entityCount;

  @Override
  public List<Citizen> getAll() {
    log.info("CitizenService method getAll started working");
    return citizenRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public Citizen getById(Long id) {
    log.info("CitizenService method get started working");
    return citizenRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
  }

  @Override
  public Citizen create(Citizen citizenDto, PassportDto passportDto) {
    log.info("CitizenService method create started working");
    var citizen = citizenRepository.save(citizenDto);
    passportServerFeignClient.create(citizen.getId(), passportDto);
    logService.sendContinuousLogToOCService(incrementLogCount(), ModelName.CITIZENS);
    return citizen;
  }

  @Override
  public Citizen edit(Long id, Citizen citizen) {
    log.info("CitizenService method edit started working");
    var editCitizen = citizenRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    editCitizen.setName(citizen.getName());

    return citizenRepository.save(editCitizen);
  }

  @Override
  public void delete(Long citizenId) {
    log.info("CitizenService method delete started working");
      int countDeletedCitizens = 1;
      boolean isSuccess = citizenRepository.setDeleteStatusById(citizenId) == countDeletedCitizens;
    if (isSuccess){
      throw new NotFoundException(ExceptionMessage.NOT_FOUND);
    }
    logService.sendContinuousLogToOCService(decrementLogCount(), ModelName.CITIZENS);
    deleteEventProducer.sendMessage(new CitizenDeleteEventMessage(citizenId));
  }

  @Override
  public List<CarResponseDto> getCarsById(Long ownerId) {
    log.info("CitizenService method getCarsById started working");
    return carServerFeignClient.getAllCarsByOwnerId(ownerId);
  }

  @Override
  public List<PassportDto> getPassportByFirstLetterInLastName(String firstLetter) {
    return passportServerFeignClient.findAllByOwnersIds(
        citizenRepository.findAllByFirstLetterInLastName(firstLetter)
            .stream()
            .map(Citizen::getId)
            .collect(Collectors.toList()));
  }

  @Override
  public void rollbackDeletedById(Long citizenId) {
    citizenRepository.findById(citizenId)
        .ifPresent(citizen -> citizen.setStatus(Status.ACTIVE));
    incrementLogCount();
  }

  @Override
  public Integer incrementLogCount(){
    return entityCount.addAndGet(1);
  }

  @Override
  public Integer addToLogCount(Integer integer){
    return entityCount.addAndGet(integer);
  }

  @Override
  public Integer getLogCount() {
    return entityCount.get();
  }

  private Integer decrementLogCount(){
    return entityCount.addAndGet(-1);
  }

  @Override
  @Scheduled(fixedDelay = 1000*60*5)
  public void sendLogByTimer(){
    logService.sendLogByTimerToOCService(entityCount.get(),ModelName.CITIZENS);
  }
}
