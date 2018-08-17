package com.robertomagale.ratecalculator.calculator;

public final class CompoundInterestMonthlyPaymentCalculator implements MonthlyPaymentCalculator {

    public double calculate(int loanAmount, int numberOfTerms, double annualInterestRate) {
        if (loanAmount <= 0) throw new IllegalArgumentException("Loan amount cannot be smaller or equal to zero");
        if (numberOfTerms <= 0) throw new IllegalArgumentException("Terms cannot be smaller or equal to zero");
        if (annualInterestRate < 0) throw new IllegalArgumentException("Interest cannot be smaller than zero");


        double monthlyInterestRate = Math.pow(1 + annualInterestRate, 1 / 12D) - 1;
        double numerator = loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfTerms);
        double denominator = Math.pow(1 + monthlyInterestRate, numberOfTerms) - 1;
        return numerator / denominator;
    }
}
