package com.mahov.cars.util;

import com.mahov.cars.proxy.lottery.ChangingLotteryResults;
import com.mahov.dto.CitizenDto;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LotteryUtilImpl implements LotteryUtil {
  Random random;

  @Override
  @ChangingLotteryResults
  public CitizenDto getRandomCitizen(List<CitizenDto> dtoList){
    return dtoList.get(random.nextInt(0,dtoList.size()-1));
  }
}
