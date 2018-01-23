package com.example.billing;

import com.example.payments.Gateway;
import com.example.payments.RecurlyGateway;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class BillingApplication {

	@Value("${queue-name}")
	String queueName;

	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);
	}

	@Bean
	public Gateway gateway(){
		return new RecurlyGateway();
	}


	@Bean
	public Queue queue(){
		return new Queue(queueName);
	}

	@Bean
	RabbitBillingListener rabbitBillingListener(){
		return new RabbitBillingListener();
	}
}
