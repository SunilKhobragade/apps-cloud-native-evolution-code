package com.example.billing;

import com.example.payments.Gateway;
import com.example.payments.RecurlyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reoccurringPayment")
public class Controller {

    Gateway recurlyGateway = new RecurlyGateway();

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> reoccurringPayment(@RequestBody Map<String, Object> params) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-type", MediaType.APPLICATION_JSON.toString());

        int paymentMonthlyAmount = (int) params.get("amount");
        String userId = (String) params.get("userId");
        System.out.println(" user id :"+ userId);
        System.out.println(" amount : "+paymentMonthlyAmount);

        if(recurlyGateway.createReocurringPayment(paymentMonthlyAmount))
        {
            //return new ResponseEntity<>(HttpStatus.CREATED);
            return new ResponseEntity<>("{errors: []}", responseHeaders, HttpStatus.CREATED);
        }else{
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("{errors: [\"error1\", \"error2\"]}", responseHeaders, HttpStatus.BAD_REQUEST);
        }

    }
}
