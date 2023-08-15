package com.mkmvpfam.gclogbook.data;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {
	@GetMapping(produces = "application/json")
	public ResponseEntity<String> health() {
		return new ResponseEntity<>("{\"status\": \"UP\"}", null, HttpStatus.OK);
	}
}
