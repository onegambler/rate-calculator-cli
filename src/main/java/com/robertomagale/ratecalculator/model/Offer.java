package com.robertomagale.ratecalculator.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
public class Offer implements Comparable<Offer> {

    private String name;
    private double annualInterestRate;
    private int amount;

    public int compareTo(Offer other) {
        int compare = Double.compare(annualInterestRate, other.annualInterestRate);
        if (compare == 0) {
            compare = Double.compare(other.amount, amount);
        }
        return compare;
    }
}
