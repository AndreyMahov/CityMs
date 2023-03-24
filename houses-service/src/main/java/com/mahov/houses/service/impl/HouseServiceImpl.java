package com.mahov.houses.service.impl;


import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import com.mahov.enums.ExceptionMessage;
import com.mahov.enums.ModelName;
import com.mahov.enums.Status;
import com.mahov.exception.NotFoundException;
import com.mahov.houses.domain.model.House;
import com.mahov.houses.domain.model.Owner;
import com.mahov.houses.kafka.PassportDeleteEventProducer;
import com.mahov.houses.kafka.rollback.CarRollbackDeleteEventProducer;
import com.mahov.houses.repository.HouseRepository;
import com.mahov.houses.repository.OwnerRepository;
import com.mahov.houses.service.HouseService;
import com.mahov.log.kafka.service.LogService;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional
@Slf4j
public class HouseServiceImpl implements HouseService {
  OwnerRepository ownerRepository;
  HouseRepository houseRepository;
  PassportDeleteEventProducer passportDeleteEventProducer;
  CarRollbackDeleteEventProducer carRollbackDeleteEventProducer;
  LogService logService;
  AtomicInteger entityCount;


  @Override
  public List<House> getAll() {
    log.info("HouseService method getAll started working");
    return houseRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public House getById(Long id) {
    log.info("HouseService method get started working");
    return houseRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
  }

  @Override
  public House create(House house) {
    log.info("HouseService method create started working");
    var result = houseRepository.save(house);
    logService.sendContinuousLogToOCService(entityCount.addAndGet(1), ModelName.HOUSE);
    return result;
  }

  @Override
  public House edit(Long id, House houseCreateDto) {
    log.info("HouseService method edit started working");
    var house = houseRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    house.setStreet(houseCreateDto.getStreet());
    house.setNumber(houseCreateDto.getNumber());
    house.getOwners().addAll(houseCreateDto.getOwners());

    return houseRepository.save(house);
  }

  @Override
  public void delete(Long id) {
    log.info("HouseService method delete started working");
    var house = houseRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    logService.sendContinuousLogToOCService(entityCount.addAndGet(-1), ModelName.HOUSE);
    house.setStatus(Status.DELETED);
  }

  @Override
  public List<Long> getOwners(String street) {
    log.info("HouseService method getOwners started working");
    return houseRepository.getAllHouseOnStreet(street).stream()
        .flatMap(h -> h.getOwners().stream())
        .map(Owner::getId)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteOwnerById(Long ownerId) {
    try {
      ownerRepository.findByCitizenIdAndStatus(ownerId, Status.ACTIVE)
          .ifPresent(owner1 -> owner1.setStatus(Status.OWNER_DELETED));
      passportDeleteEventProducer.sendMessage(new CitizenDeleteEventMessage(ownerId));
    } catch (RuntimeException e) {
      carRollbackDeleteEventProducer.sendMessage(new CitizenRollBackDeleteEventMessage(ownerId));
    }
  }

  @Override
  public void rollbackDeletedUserById(Long citizenId) {
    var owner = ownerRepository.findByCitizenId(citizenId)
        .orElseThrow(()-> new NotFoundException(ExceptionMessage.NOT_FOUND));
    owner.setStatus(Status.ACTIVE);
    ownerRepository.save(owner);
    carRollbackDeleteEventProducer.sendMessage(new CitizenRollBackDeleteEventMessage(citizenId));
  }

  @Override
  @Scheduled(fixedDelay = 1000*60*5)
  public void sendLogByTimer(){
    logService.sendLogByTimerToOCService(entityCount.get(),ModelName.HOUSE);
  }
}
