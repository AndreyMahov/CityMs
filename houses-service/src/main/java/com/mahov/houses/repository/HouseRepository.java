package com.mahov.houses.repository;

import com.mahov.enums.Status;
import com.mahov.houses.domain.model.House;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HouseRepository extends JpaRepository<House, Long> {

  @Query("SELECT h from House h where h.street = lower(?1) or h.street = UPPER(?1)")
  List<House> getAllHouseOnStreet(String street);

  List<House> findAllByStatus(Status status);

  Optional<House> findByIdAndStatus(Long id, Status status);
}
