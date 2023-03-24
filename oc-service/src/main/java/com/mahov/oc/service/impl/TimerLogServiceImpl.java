package com.mahov.oc.service.impl;

import com.mahov.oc.model.entity.TimerLog;
import com.mahov.oc.repository.TimerLogRepository;
import com.mahov.oc.service.TimerLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimerLogServiceImpl implements TimerLogService {
  TimerLogRepository timerLogRepository;


  @Override
  public void saveTimerLog(TimerLog LogByTimer) {
    timerLogRepository.save(LogByTimer);
  }
}
