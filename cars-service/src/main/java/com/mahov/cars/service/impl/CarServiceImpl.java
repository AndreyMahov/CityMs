package com.mahov.cars.service.impl;

import com.mahov.cars.domain.model.Car;
import com.mahov.cars.kafka.delete.citizen.HouseDeleteEventProducer;
import com.mahov.cars.kafka.delete.citizen.rollback.CitizenRollbackDeleteEventProducer;
import com.mahov.cars.repository.CarRepository;
import com.mahov.cars.service.CarService;
import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import com.mahov.enums.ExceptionMessage;
import com.mahov.enums.ModelName;
import com.mahov.enums.Status;
import com.mahov.exception.CarCreateException;
import com.mahov.exception.NotFoundException;
import com.mahov.log.kafka.service.LogService;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
  CarRepository carRepository;
  HouseDeleteEventProducer houseDeleteEventProducer;
  CitizenRollbackDeleteEventProducer citizenRollbackDeleteEventProducer;
  LogService logService;
  AtomicInteger entityCount;
  AtomicBoolean isLotteryGoingNow;

  @Override
  public List<Car> getAllActive() {
    log.info("СarService method getAll started working");
    return carRepository.findAllByStatus(Status.ACTIVE);
  }

  @Override
  public Car getActiveByid(Long id) {
    log.info("СarService method get started working");
    return carRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
  }

  @Override
  public Car create(Car car) {
    log.info("СarService method create started working");
    if (isLotteryGoingNow.get()){
      throw new CarCreateException(ExceptionMessage.SELLER_BUSY);
    }
    var result = carRepository.save(car);
    logService.sendContinuousLogToOCService(entityCount.addAndGet(1), ModelName.CAR);
    return result;
  }

  @Override
  public Car edit(Long id, Car carRequestDto) {
    log.info("СarService method edit started working");
    var car = carRepository.findByIdAndStatus(id, Status.ACTIVE)
        .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND));
    boolean isFederalNumberStillSame = !car.getFederalNumber()
        .equals(carRequestDto.getFederalNumber());

    if (isFederalNumberStillSame) {
      car.setFederalNumber(carRequestDto.getFederalNumber());
    }

    return carRepository.save(car);
  }

  @Override
  public void delete(Long id) {
    log.info("СarService method delete started working");
    if (carRepository.existsById(id)) {
      carRepository.setDeleteStatusById(id);
    } else {
      throw new NotFoundException(ExceptionMessage.NOT_FOUND);
    }
    logService.sendContinuousLogToOCService(entityCount.addAndGet(1), ModelName.CAR);
  }

  @Override
  public void citizenDeleteEvent(Long ownerId) {
    try {
      carRepository.setDeleteStatusByOwnerId(ownerId);
      houseDeleteEventProducer.sendMessage(new CitizenDeleteEventMessage(ownerId));
    } catch (RuntimeException e) {
      citizenRollbackDeleteEventProducer.sendMessage(
          new CitizenRollBackDeleteEventMessage(ownerId));
    }
  }

  @Override
  public void rollbackCitizenDeletedEvenByOwnerId(Long ownerId) {
    List<Car> cars = carRepository.findAllByOwnerId(ownerId);
    cars.stream()
        .forEach(car -> car.setStatus(Status.ACTIVE));
    logService.sendContinuousLogToOCService(entityCount.addAndGet(carRepository.saveAll(cars).size()), ModelName.CAR);
    citizenRollbackDeleteEventProducer.sendMessage(new CitizenRollBackDeleteEventMessage(ownerId));
  }

  @Override
  public List<Car> getAllByOwnerId(Long ownerId) {
    return carRepository.findAllByOwnerId(ownerId);
  }

  @Override
  public void deleteByOwnerId(Long ownerId) {
    carRepository.setDeleteStatusByOwnerId(ownerId);
  }

  @Override
  @Scheduled(fixedDelay = 1000*60*5)
  public void sendLogByTimer(){
    logService.sendLogByTimerToOCService(entityCount.get(),ModelName.CAR);
  }
}