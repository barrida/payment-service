package com.example.finance.service;

/**
 * @author Suleyman Yildirim
 */

import com.example.finance.constants.Topics;
import com.example.finance.dto.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentValidationConsumer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentValidationConsumer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = Topics.PAYMENT_CREATED, groupId = "payment-validation-service")
    public void consume(PaymentEvent event) {
        System.out.println("Consumed PAYMENT_CREATED: " + event);

        // Fake validation
        event.setStatus("VALIDATED");

        kafkaTemplate.send(Topics.PAYMENT_VALIDATED, event.getPaymentId(), event);
        System.out.println("📤 Sent PAYMENT_VALIDATED: " + event);
    }
}
