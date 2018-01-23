package com.example.billing;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class HttpBillingClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpBillingClient.class);

    private String serviceURL;
    //private RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplate;

    public HttpBillingClient(String serviceURL) {
        this.serviceURL = serviceURL;
        //this.restTemplate = new RestTemplate();
    }


    @HystrixCommand(fallbackMethod = "defaultBillingService")
    public void billUser(String userId, int paymentMonthlyAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("amount", paymentMonthlyAmount);
        //restTemplate.postForEntity(serviceURL+"/reoccurringPayment",params, String.class);
        restTemplate.postForEntity(serviceURL,params, String.class);
        logger.info("Real billing service is  used.");
        logger.info("UserId: "+userId + " : amount : "+paymentMonthlyAmount);
    }

    public void defaultBillingService(String userId, int paymentMonthlyAmount) {
        logger.info("Default billing service is  used.");
        logger.info("UserId: "+userId + " : amount : "+paymentMonthlyAmount);
    }

}
