package com.example.ums.subscriptions;

import com.example.billing.BillingClient;
import com.example.billing.HttpBillingClient;
import com.example.email.SendEmail;
import com.example.subscriptions.CreateSubscription;
import com.example.subscriptions.Subscription;
import com.example.subscriptions.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/subscriptions")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    SubscriptionRepository subscriptions;

    String billingClientUrl;

    //private HttpBillingClient httpBillingClient;

    /*@Autowired
    HttpBillingClient httpBillingClient;*/

    @Autowired
    BillingClient billingClient;

    /*public Controller(@Autowired HttpBillingClient httpBillingClient, EurekaClient discoveryClient){
        this.httpBillingClient = httpBillingClient;
        this.discoveryClient = discoveryClient;
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Subscription> index() {
        return subscriptions.all();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Map<String, String> params) {

        //ChargeUser paymentCreator = new ChargeUser(new RecurlyGateway());
        //HttpBillingClient httpBillingClient = new HttpBillingClient(billingClientUrl);
        SendEmail emailSender = new SendEmail();

        /*new CreateSubscription(paymentCreator, emailSender, subscriptions)
                .run(params.get("userId"), params.get("packageId"));*/

       /* new CreateSubscription(httpBillingClient, emailSender, subscriptions)
                .run(params.get("userId"), params.get("packageId"));*/

        new CreateSubscription(billingClient, emailSender, subscriptions)
                .run(params.get("userId"), params.get("packageId"));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
