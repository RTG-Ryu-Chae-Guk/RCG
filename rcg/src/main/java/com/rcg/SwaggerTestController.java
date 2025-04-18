package com.rcg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerTestController {

    @GetMapping("/api/test")
    public String testSwagger() {
        return "Swagger is working!";
    }
}
