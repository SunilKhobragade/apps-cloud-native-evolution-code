package com.example.billing;

import lombok.Data;

import java.io.Serializable;

@Data
public class BillingMessage implements Serializable{

    private String userId;
    private int amount;

    public BillingMessage(String userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }

}
