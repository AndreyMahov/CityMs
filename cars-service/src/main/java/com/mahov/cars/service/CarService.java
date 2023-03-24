package com.mahov.cars.service;

import com.mahov.cars.domain.model.Car;
import java.util.List;

public interface CarService {

  List<Car> getAllActive();

  Car getActiveByid(Long id);

  Car create(Car car);

  Car edit(Long id, Car car);

  void delete(Long id);

  void citizenDeleteEvent(Long id);

  List<Car> getAllByOwnerId(Long ownerId);

  void rollbackCitizenDeletedEvenByOwnerId(Long citizenId);

  void deleteByOwnerId(Long id);

  void sendLogByTimer();
}
