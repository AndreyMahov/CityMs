package com.mahov.citizens.service.feign;

import com.mahov.dto.request.LogCreateRequestDto;
import com.mahov.dto.response.LogResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "${feign.client.names.oc-service}", url = "${url.oc-service}")
public interface OcContinuousLogServiceFeignClient {

  @GetMapping("/continuous/citizens")
  LogResponseDto getCitizensCountFromOcService();

  @PostMapping()
  LogResponseDto create(@RequestBody LogCreateRequestDto requestDto);
}
