package com.mahov.cars.proxy.lottery;

import com.mahov.dto.CitizenDto;
import com.mahov.enums.PlaceWork;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MyAspect {
  Random random;

  @SneakyThrows
  @Around(value = "@annotation(com.mahov.cars.proxy.lottery.ChangingLotteryResults)", argNames = "joinPoint")
  public Object getUnRandomCitizen(ProceedingJoinPoint joinPoint) {
    var winner = (CitizenDto) joinPoint.proceed();
    Object[] args = joinPoint.getArgs();
    List<CitizenDto> dtoList = (List<CitizenDto>) args[0];
    boolean isWinnerNotFromFactory = winner.getPlaceWork().equals(PlaceWork.ANOTHER);

    return isWinnerNotFromFactory ? chooseRandomFactoryWorker(dtoList) : winner ;
  }

  private CitizenDto chooseRandomFactoryWorker(List<CitizenDto> dtoList){
    List<CitizenDto> factoryWorkers = dtoList
        .stream()
        .filter(citizenDto -> citizenDto.getPlaceWork().equals(PlaceWork.FACTORY))
        .toList();

    return factoryWorkers.get(random.nextInt(0, factoryWorkers.size() - 1));
  }
}
