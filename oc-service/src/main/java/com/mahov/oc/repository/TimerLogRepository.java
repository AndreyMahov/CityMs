package com.mahov.oc.repository;

import com.mahov.oc.model.entity.TimerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerLogRepository extends JpaRepository<TimerLog, Long> {

}
