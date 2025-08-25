package com.example.finance.service;

import com.example.finance.constants.Topics;
import com.example.finance.dto.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Suleyman Yildirim
 */

@Service
public class PaymentCompletionConsumer {
    @KafkaListener(topics = Topics.PAYMENT_PROCESSED, groupId = "payment-completion-service")
    public void consume(PaymentEvent event) {
        System.out.println("Consumed PAYMENT_PROCESSED: " + event);

        // Fake completion
        event.setStatus("COMPLETED");

        System.out.println(" Payment COMPLETED: " + event);
    }
}
