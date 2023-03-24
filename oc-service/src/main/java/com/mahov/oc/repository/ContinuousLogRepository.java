package com.mahov.oc.repository;

import com.mahov.oc.model.entity.ContinuousLog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContinuousLogRepository extends JpaRepository<ContinuousLog, Long> {

    @Query("select l from ContinuousLog l where l.timestamp = (select MAX(l.timestamp) from ContinuousLog l where UPPER(l.modelName) = UPPER(:modelName))")
    Optional<ContinuousLog> findLastLogByModelName(@Param("modelName") String modelName);

    @Query("select l from ContinuousLog l where l.timestamp between :startDate and :endDate")
    List<ContinuousLog> findLogsInTimeSpan(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
