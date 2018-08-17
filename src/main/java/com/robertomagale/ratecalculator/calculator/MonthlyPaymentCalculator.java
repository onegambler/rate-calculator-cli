package com.robertomagale.ratecalculator.calculator;

public interface MonthlyPaymentCalculator {

    /**
     * It calculates a monthly payment for a loan, based on the requested amount, number of terms and interest rate.
     *
     * @param requestedLoan amount requested
     * @param numberOfTerms number of instalments
     * @param annualInterestRate the annual interest rate to apply
     * @return the monthly payment
     */
    double calculate(
            int requestedLoan,
            int numberOfTerms,
            double annualInterestRate);
}
