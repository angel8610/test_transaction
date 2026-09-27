package com.example.transaction.client.openfiengs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "testClient", url = "${external.api.transaction.url}/test")
public interface TestClient {

  @GetMapping("/successfully")
  String testServerSuccessfully();


}
