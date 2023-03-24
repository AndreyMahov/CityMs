package com.mahov.oc.service;

import com.mahov.oc.model.entity.TimerLog;

public interface TimerLogService {

  void saveTimerLog(TimerLog toLogByTimer);
}
