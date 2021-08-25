package com.example.gateway.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
	
	@GetMapping("fallback/serviceA")
	public String fallbackA(Throwable th) {
		return "Gateway : Fallback Message - Service A is not available";
	}
	
	@GetMapping("fallback/serviceB")
	public String fallbackB(Throwable th) {
		return "Gateway : Fallback Message - Service B is not available";
	}
	
}
