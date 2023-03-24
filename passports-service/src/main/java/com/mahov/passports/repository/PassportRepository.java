package com.mahov.passports.repository;

import com.mahov.enums.Status;
import com.mahov.passports.domain.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {

  Passport findByOwnerId(Long id);

  List<Passport> findAllByStatus (Status status);

  Optional<Passport> findByIdAndStatus(Long id, Status status);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE Passport s set s.status = 'DELETE' WHERE s.ownerId =:ownerId ")
  Integer setDeleteStatusByOwnerId(@Param("ownerId") Long id);
}
