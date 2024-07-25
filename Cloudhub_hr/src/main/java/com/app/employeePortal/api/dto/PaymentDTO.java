package com.app.employeePortal.api.dto;

import java.math.BigDecimal;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
	
    private String paymentType;

    //  @NotNull(message = "amount must not be null")
    private BigDecimal amount;
    
    private BigDecimal remainingBalance;

    private String currency;

    private String paymentId;

    private String status;

    private String paymentMethodId;

    private String serviceId;

    private String requirementId;

    private String stripePaymentId;

    private boolean stripePaymentInd;

}
