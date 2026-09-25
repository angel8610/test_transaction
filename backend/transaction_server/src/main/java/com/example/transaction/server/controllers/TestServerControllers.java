package com.example.transaction.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/server/transaction/test")
public class TestServerControllers {

  @GetMapping("/successfully")
  public ResponseEntity<String> successfully() {
    return ResponseEntity.ok("Test server successfully");
  }


}
