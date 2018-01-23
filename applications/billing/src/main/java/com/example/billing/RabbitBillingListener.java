package com.example.billing;
import com.example.payments.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;

public class RabbitBillingListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitBillingListener.class);

    @Autowired
    Gateway recurlyGateway;

    @RabbitListener(queues = "${queue-name}")
    public void process(@Payload BillingMessage message){

        if(recurlyGateway.createReocurringPayment(message.getAmount()))
        {
           logger.info("Success message " + " userId : " + message.getUserId() + " amount : "+message.getAmount() );
        }else{
            logger.info("Error message ");
        }
    }

}
