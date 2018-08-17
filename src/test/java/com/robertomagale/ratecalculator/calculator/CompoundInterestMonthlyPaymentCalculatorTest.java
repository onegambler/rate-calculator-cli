package com.robertomagale.ratecalculator.calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.data.Percentage;
import org.junit.Test;

public class CompoundInterestMonthlyPaymentCalculatorTest {

    private MonthlyPaymentCalculator monthlyPaymentCalculator = new CompoundInterestMonthlyPaymentCalculator();

    @Test
    public void shouldCorrectlyCalculateTheRate() {
        double payment = monthlyPaymentCalculator.calculate(1_000, 36, 0.7);
        assertThat(payment).isCloseTo(56.76536819D, Percentage.withPercentage(0.00001));
    }

    @Test
    public void shouldThrowExceptionIfRequestedLoanIsSmallerOrEqualToZero() {

        assertThatThrownBy(() -> monthlyPaymentCalculator.calculate(0, 36, 0.7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan amount cannot be smaller or equal to zero");

        assertThatThrownBy(() -> monthlyPaymentCalculator.calculate(-10, 36, 0.7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan amount cannot be smaller or equal to zero");
    }

    @Test
    public void shouldThrowExceptionIfNumberOfTermsIsSmallerOrEqualToZero() {

        assertThatThrownBy(() -> monthlyPaymentCalculator.calculate(10, 0, 0.7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Terms cannot be smaller or equal to zero");

        assertThatThrownBy(() -> monthlyPaymentCalculator.calculate(10, -1, 0.7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Terms cannot be smaller or equal to zero");
    }

    @Test
    public void shouldThrowExceptionIfRateIsSmallerThanZero() {

        assertThatThrownBy(() -> monthlyPaymentCalculator.calculate(10, 10, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Interest cannot be smaller than zero");
    }
}