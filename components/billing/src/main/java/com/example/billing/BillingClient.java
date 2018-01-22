package com.example.billing;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class BillingClient {

    private static final Logger logger = LoggerFactory.getLogger(BillingClient.class);

    private String serviceURL;
    //private RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplate;

    public BillingClient(String serviceURL) {
        this.serviceURL = serviceURL;
        //this.restTemplate = new RestTemplate();
    }


    @HystrixCommand(fallbackMethod = "defaultBillingService")
    public void billUser(String userId, int paymentMonthlyAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("amount", paymentMonthlyAmount);
        //restTemplate.postForEntity(serviceURL+"/reoccurringPayment",params, String.class);
        restTemplate.postForEntity("http://BILLING"+"/reoccurringPayment",params, String.class);
        logger.info("Real billing service is  used.");
        logger.info("UserId: "+userId + " : amount : "+paymentMonthlyAmount);
    }

    public void defaultBillingService(String userId, int paymentMonthlyAmount) {
        logger.info("Default billing service is  used.");
        logger.info("UserId: "+userId + " : amount : "+paymentMonthlyAmount);
    }

}
