package com.mahov.cars.util;

import com.mahov.dto.CitizenDto;
import java.util.List;

public interface LotteryUtil {

    CitizenDto getRandomCitizen(List<CitizenDto> citizenDtos);
}
