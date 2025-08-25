package com.example.finance.service;

import com.example.finance.constants.Topics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Suleyman Yildirim
 */

@Service
public class AuditConsumer {

    @KafkaListener(topics = {Topics.PAYMENT_CREATED, Topics.PAYMENT_COMPLETED, Topics.PAYMENT_PROCESSED, Topics.PAYMENT_VALIDATED})
    public void audit(Object message){
        System.out.println("AUDIT LOG: " + message);
        // todo - later save into PostgreSQL

    }
}
