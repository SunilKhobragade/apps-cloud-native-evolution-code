package com.example.billing;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitBillingClient implements BillingClient {


    private RabbitTemplate rabbitTemplate;

    private String queueName;

    public RabbitBillingClient(String queueName, RabbitTemplate rabbitTemplate) {
        this.queueName = queueName;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void billUser(String userId, int paymentMonthlyAmount) {
        BillingMessage billingMessage = new BillingMessage(userId,paymentMonthlyAmount);
        rabbitTemplate.convertAndSend(queueName,billingMessage);
    }
}
