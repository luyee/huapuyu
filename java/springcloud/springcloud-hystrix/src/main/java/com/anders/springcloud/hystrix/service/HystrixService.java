package com.anders.springcloud.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HystrixService {

    @Autowired
    private CallDependencyService callDependencyService;
    
    public String callDependencyService() {
        return callDependencyService.mockGetUserInfo();
    }
}
