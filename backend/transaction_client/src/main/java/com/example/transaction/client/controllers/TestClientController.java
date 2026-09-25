package com.example.transaction.client.controllers;

import com.example.transaction.client.openfiengs.TestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction/test")
public class TestClientController {

  private final TestClient testClient;

  public TestClientController(TestClient testClient) {
    this.testClient = testClient;
  }

  @GetMapping("/successfully")
  public ResponseEntity<String> testSuccessfully() {
    return ResponseEntity.ok("Tests client success");
  }

  @GetMapping("/external")
  public ResponseEntity<String> tesClientSuccessfully() {
    return ResponseEntity.ok(this.testClient.testServerSuccessfully());
  }


}
