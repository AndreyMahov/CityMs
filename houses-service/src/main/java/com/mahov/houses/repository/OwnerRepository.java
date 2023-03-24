package com.mahov.houses.repository;

import com.mahov.enums.Status;
import com.mahov.houses.domain.model.Owner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

  Optional<Owner> findByCitizenIdAndStatus(Long id, Status status);

  Optional<Owner> findByCitizenId(Long id);
}
