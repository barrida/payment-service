package com.example.finance.service;

import com.example.finance.constants.Topics;
import com.example.finance.dto.PaymentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Unit tests for PaymentProducer
 * @author Suleyman Yildirim
 */
@ExtendWith(MockitoExtension.class)
class PaymentProducerTest {

    @Mock
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @InjectMocks
    private PaymentProducer paymentProducer;

    @Test
    void shouldSendPaymentCreatedEvent() {
        // Given
        PaymentEvent paymentEvent = createTestPaymentEvent();

        // When
        paymentProducer.sendPaymentCreated(paymentEvent);

        // Then
        verify(kafkaTemplate, times(1)).send(
                eq(Topics.PAYMENT_CREATED),
                eq(paymentEvent.getPaymentId()),
                eq(paymentEvent)
        );
    }

    @Test
    void shouldSendPaymentEventWithCorrectPaymentId() {
        // Given
        PaymentEvent paymentEvent = createTestPaymentEvent();
        String expectedPaymentId = "payment-123";
        paymentEvent.setPaymentId(expectedPaymentId);

        // When
        paymentProducer.sendPaymentCreated(paymentEvent);

        // Then
        verify(kafkaTemplate).send(
                eq(Topics.PAYMENT_CREATED),
                eq(expectedPaymentId),
                eq(paymentEvent)
        );
    }

    @Test
    void shouldSendPaymentEventWithDifferentStatuses() {
        // Given
        PaymentEvent createdEvent = createTestPaymentEvent();
        createdEvent.setStatus("CREATED");

        PaymentEvent validatedEvent = createTestPaymentEvent();
        validatedEvent.setPaymentId("payment-456");
        validatedEvent.setStatus("VALIDATED");

        // When
        paymentProducer.sendPaymentCreated(createdEvent);
        paymentProducer.sendPaymentCreated(validatedEvent);

        // Then
        verify(kafkaTemplate).send(
                eq(Topics.PAYMENT_CREATED),
                eq("payment-123"),
                eq(createdEvent)
        );

        verify(kafkaTemplate).send(
                eq(Topics.PAYMENT_CREATED),
                eq("payment-456"),
                eq(validatedEvent)
        );
    }

    private PaymentEvent createTestPaymentEvent() {
        PaymentEvent event = new PaymentEvent();
        event.setPaymentId("payment-123");
        event.setUserId("user-456");
        event.setAmount(new BigDecimal("100.50"));
        event.setStatus("CREATED");
        return event;
    }
}