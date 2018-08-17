package com.robertomagale.ratecalculator;

import static java.util.Objects.requireNonNull;

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

        requireNonNull(quoteWriter, "Quote writer cannot be null");
        requireNonNull(monthlyPaymentCalculator, "Monthly payment calculator cannot be null");
        requireNonNull(dataReader, "Data reader cannot be null");
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
            int offerLoanedAmount = getOfferLoanedAmount(leftLoanAmount, offer);

            totalMonthlyPayment += monthlyPaymentCalculator.calculate(
                    offerLoanedAmount,
                    numberOfTerms,
                    offer.getAnnualInterestRate());

            leftLoanAmount -= offerLoanedAmount;
            totalLoanRate += offer.getAnnualInterestRate();
            totalLoaners++;
            if (isLoanComplete(leftLoanAmount)) {
                break;
            }
        }

        if (!isLoanComplete(leftLoanAmount)) {
            quoteWriter.writeInsufficientFunding(leftLoanAmount);
        } else {
            double totalRate = totalLoanRate / totalLoaners;
            double totalRepayment = totalMonthlyPayment * numberOfTerms;

            Quote quote = Quote.builder()
                    .requestedAmount(loanAmount)
                    .totalRate(totalRate)
                    .monthlyRepayment(totalMonthlyPayment)
                    .totalRepayment(totalRepayment)
                    .build();

            quoteWriter.writeQuote(quote);
        }
    }

    private int getOfferLoanedAmount(int leftLoanAmount, Offer offer) {
        return Math.min(leftLoanAmount, offer.getAmount());
    }

    private boolean isLoanComplete(int leftAmount) {
        return leftAmount == 0;
    }
}
