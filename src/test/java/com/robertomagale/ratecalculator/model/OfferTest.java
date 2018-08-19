package com.robertomagale.ratecalculator.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OfferTest {

    @Test
    public void shouldCorrectlyCompareIfDifferentRate() {
        Offer firstOffer = Offer.builder().annualInterestRate(0.1).build();
        Offer secondOffer = Offer.builder().annualInterestRate(0.2).build();

        assertThat(firstOffer.compareTo(secondOffer)).isEqualTo(-1);
    }

    @Test
    public void whenRateIsSameThenCompareByAmount() {
        Offer firstOffer = Offer.builder().annualInterestRate(1).amount(10).build();
        Offer secondOffer = Offer.builder().annualInterestRate(1).amount(2).build();

        assertThat(firstOffer.compareTo(secondOffer)).isEqualTo(-1);
    }

    @Test
    public void whenObjectsAreTheSameThenReturnZero() {
        Offer firstOffer = Offer.builder().annualInterestRate(1).amount(10).build();
        Offer secondOffer = Offer.builder().annualInterestRate(1).amount(10).build();

        assertThat(firstOffer.compareTo(secondOffer)).isZero();
    }

}