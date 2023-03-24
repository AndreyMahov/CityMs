package com.mahov.cars.repository;

import com.mahov.cars.domain.model.Car;
import com.mahov.enums.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {

  List<Car> findAllByStatus(Status status);

  Optional<Car> findByIdAndStatus(Long id, Status status);

  List<Car> findAllByOwnerId(Long ownerId);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Car c set c.status = 'DELETE' WHERE c.ownerId =:ownerId ")
  void setDeleteStatusByOwnerId(@Param("ownerId") Long ownerId);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Car c set c.status = 'DELETE' WHERE c.id =:id ")
  void setDeleteStatusById(@Param("id") Long id);
}
