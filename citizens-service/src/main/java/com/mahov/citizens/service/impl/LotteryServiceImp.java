package com.mahov.citizens.service.impl;

import com.mahov.citizens.kafka.event.lottery.LotteryEventProducer;
import com.mahov.citizens.repository.CitizenRepository;
import com.mahov.citizens.service.LotteryService;
import com.mahov.dto.CitizenDto;
import com.mahov.dto.event.LotteryCarEvent;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LotteryServiceImp implements LotteryService {
CitizenRepository citizenRepository;
LotteryEventProducer lotteryEventProducer;

@Override
@Scheduled(fixedDelay = 1000*15)
public void startLottery() {
  lotteryEventProducer.sendMessage(LotteryCarEvent
      .builder()
      .citizenDtoList(
          citizenRepository
          .findAll()
          .stream()
          .map(citizen -> CitizenDto
              .builder()
              .citizenId(citizen.getId())
              .placeWork(citizen.getPlaceWork())
              .build())
          .collect(Collectors.toList()))
      .build());
  }
}
