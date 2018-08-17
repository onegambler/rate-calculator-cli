package com.robertomagale.ratecalculator.calculator;

public interface MonthlyPaymentCalculator {

//    Quote getQuote(int loanAmount, int numberOfTerms);

    double calculate(
            int requestedLoan,
            int numberOfTerms,
            double annualInterestRate);
}
