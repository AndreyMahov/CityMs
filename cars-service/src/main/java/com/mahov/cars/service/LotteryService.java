package com.mahov.cars.service;

import com.mahov.dto.event.LotteryCarEvent;

public interface LotteryService {

  void giveOutPrize(LotteryCarEvent lotteryCarEvent);
}
