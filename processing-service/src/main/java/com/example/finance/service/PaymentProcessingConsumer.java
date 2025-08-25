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
public class PaymentProcessingConsumer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentProcessingConsumer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = Topics.PAYMENT_VALIDATED, groupId = "payment-processing-service")
    public void consume(PaymentEvent event) {
        System.out.println("⚙️ Consumed PAYMENT_VALIDATED: " + event);

        // Fake processing
        event.setStatus("PROCESSED");

        kafkaTemplate.send(Topics.PAYMENT_PROCESSED, event.getPaymentId(), event);
        System.out.println("Sent PAYMENT_PROCESSED: " + event);
    }
}

