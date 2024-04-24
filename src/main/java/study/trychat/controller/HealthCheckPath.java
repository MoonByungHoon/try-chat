package study.trychat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckPath {

  @GetMapping("health-check-path")
  public ResponseEntity<?> healthCheck() {

    return ResponseEntity.ok(200);
  }
}
