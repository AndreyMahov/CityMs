package com.mahov.citizens.service.feign;

import com.mahov.dto.response.CarResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "${feign.client.names.cars-service}", url = "${url.cars-service}")
public interface CarServerFeignClient {

  @GetMapping("/owners/{ownerId}")
  List<CarResponseDto> getAllCarsByOwnerId(@PathVariable("ownerId") Long ownerId);
}
