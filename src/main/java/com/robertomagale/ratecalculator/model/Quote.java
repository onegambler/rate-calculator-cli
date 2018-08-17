package com.robertomagale.ratecalculator.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Quote {

    private int requestedAmount;
    private double totalRate;
    private double monthlyRepayment;
    private double totalRepayment;

}
