package com.mahov.citizens.repository;

import com.mahov.citizens.domain.model.Citizen;
import com.mahov.enums.Status;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

  @Query(value = "SELECT * FROM citizens " +
      "WHERE gender = 'MALE' AND UPPER(surname) LIKE UPPER(CONCAT(?1,'%'))", nativeQuery = true)
  List<Citizen> findAllByFirstLetterInLastName(String character);

  List<Citizen> findAllByStatus(Status status);

  Optional<Citizen> findByIdAndStatus(Long id, Status status);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Citizen c SET c.status = 'DELETE' WHERE c.id =:id ")
  Integer setDeleteStatusById(@Param("id") Long id);

  @Query("SELECT COUNT(c) FROM Citizen c WHERE c.status = 'ACTIVE'")
  Integer entityCount();
}
