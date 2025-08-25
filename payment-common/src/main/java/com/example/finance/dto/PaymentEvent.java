package com.example.finance.dto;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Suleyman Yildirim
 */
@Data
@Getter
@Setter
public class PaymentEvent {
    private String paymentId;
    private String userId;
    private BigDecimal amount;
    private String status; // CREATED, VALIDATED, PROCESSED, COMPLETED

}
