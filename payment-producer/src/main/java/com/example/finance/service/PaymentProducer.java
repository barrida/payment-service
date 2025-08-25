package com.example.finance.service;

import com.example.finance.constants.Topics;
import com.example.finance.dto.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Suleyman Yildirim
 */
@Service
public class PaymentProducer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentCreated(PaymentEvent event) {
        kafkaTemplate.send(Topics.PAYMENT_CREATED, event.getPaymentId(), event);
        System.out.println("Sent PAYMENT_CREATED: " + event);
    }
}
