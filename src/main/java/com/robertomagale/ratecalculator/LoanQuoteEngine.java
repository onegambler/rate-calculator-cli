package com.robertomagale.ratecalculator;

import com.robertomagale.ratecalculator.calculator.MonthlyPaymentCalculator;
import com.robertomagale.ratecalculator.model.Offer;
import com.robertomagale.ratecalculator.model.Quote;
import com.robertomagale.ratecalculator.reader.DataReader;
import com.robertomagale.ratecalculator.writer.QuoteWriter;

import java.util.Set;
import java.util.TreeSet;

public class LoanQuoteEngine {

    private final QuoteWriter quoteWriter;
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final Set<Offer> sortedOffers;

    LoanQuoteEngine(DataReader dataReader,
                    QuoteWriter quoteWriter,
                    MonthlyPaymentCalculator monthlyPaymentCalculator) {

        this.quoteWriter = quoteWriter;
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
        this.sortedOffers = new TreeSet<>(dataReader.getOffers());
    }

    public void process(int loanAmount, int numberOfTerms) {
        int leftLoanAmount = loanAmount;

        int totalLoaners = 0;
        double totalLoanRate = 0;
        double totalMonthlyPayment = 0;

        for (Offer offer : sortedOffers) {
            int offerLoanAmount = Math.min(leftLoanAmount, offer.getAmount());

            totalMonthlyPayment += monthlyPaymentCalculator.calculate(
                    offerLoanAmount,
                    numberOfTerms,
                    offer.getAnnualInterestRate());

            leftLoanAmount -= offerLoanAmount;
            totalLoanRate += offer.getAnnualInterestRate();
            totalLoaners++;
            if (isLoanComplete(leftLoanAmount)) {
                break;
            }
        }

        if (!isLoanComplete(leftLoanAmount)) {
            quoteWriter.writeInsufficientFunding(leftLoanAmount);
        } else {
            Quote quote = Quote.builder()
                    .requestedAmount(loanAmount)
                    .totalRate(totalLoanRate / totalLoaners)
                    .monthlyRepayment(totalMonthlyPayment)
                    .totalRepayment(totalMonthlyPayment * numberOfTerms)
                    .build();
            quoteWriter.writeQuote(quote);
        }
    }

    private boolean isLoanComplete(int leftAmount) {
        return leftAmount == 0;
    }
}
