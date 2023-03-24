package com.mahov.houses.service;


import com.mahov.houses.domain.model.House;
import java.util.List;


public interface HouseService {

  List<House> getAll();

  House getById(Long id);

  House create(House houseCreateDto);

  House edit(Long id, House houseCreateDto);

  void delete(Long id);

  List<Long> getOwners(String street);

  void deleteOwnerById(Long ownerId);

  void rollbackDeletedUserById(Long citizenId);

  void sendLogByTimer();
}
