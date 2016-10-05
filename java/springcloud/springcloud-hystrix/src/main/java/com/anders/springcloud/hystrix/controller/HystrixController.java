package com.anders.springcloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anders.springcloud.hystrix.service.HystrixService;

@RestController
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;
    
    @RequestMapping("/call")
    public String callDependencyService(){
        return hystrixService.callDependencyService();
    }
}
