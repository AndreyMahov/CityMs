package com.mahov.cars.service.impl;

import com.mahov.cars.domain.model.Car;
import com.mahov.cars.repository.CarRepository;
import com.mahov.cars.service.LotteryService;
import com.mahov.cars.util.LotteryUtil;
import com.mahov.dto.event.LotteryCarEvent;
import com.mahov.enums.ModelName;
import com.mahov.enums.Status;
import com.mahov.log.kafka.service.LogService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {
  CarRepository carRepository;
  LotteryUtil lotteryUtil;
  AtomicBoolean isLotteryGoingNow ;
  LogService logService;
  AtomicInteger atomicInteger;

  @Override
  public void giveOutPrize(LotteryCarEvent lotteryCarEvent) {
    isLotteryGoingNow.set(true);
    var winner = lotteryUtil.getRandomCitizen(lotteryCarEvent.getCitizenDtoList());
    createPrize(winner.getCitizenId());
    log.info("Winner from " + winner.getPlaceWork() + " with id " + winner.getCitizenId());
    isLotteryGoingNow.set(false);
  }

  private void createPrize(Long winnerId){
    carRepository.save(Car
        .builder()
        .ownerId(winnerId)
        .federalNumber("WINNER")
        .status(Status.ACTIVE)
        .build());
    logService.sendContinuousLogToOCService(atomicInteger.addAndGet(1), ModelName.CAR);
  }
}
