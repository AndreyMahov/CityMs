package com.mahov.citizens.service.feign;

import com.mahov.dto.PassportDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "${feign.client.names.passports-service}", url = "${url.passports-service}")
public interface PassportServerFeignClient {

  @PostMapping("/{ownerId}")
  PassportDto create(@PathVariable Long ownerId, @RequestBody PassportDto passportDto);

  @PostMapping()
  PassportDto create( @RequestBody List<PassportDto> passportDto);

  @GetMapping("/owners")
  List<PassportDto> findAllByOwnersIds(@RequestBody List<Long> ownersIds);
}
