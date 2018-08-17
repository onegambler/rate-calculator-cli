package com.robertomagale.ratecalculator.writer;

import static java.math.RoundingMode.HALF_UP;

import com.robertomagale.ratecalculator.model.Quote;

import java.text.DecimalFormat;

public class ConsoleQuoteWriter implements QuoteWriter {

    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("£#.00");
    private static final DecimalFormat PERCENTAGE_FORMATTER = new DecimalFormat("#.0%");

    static {
        AMOUNT_FORMATTER.setRoundingMode(HALF_UP);
        PERCENTAGE_FORMATTER.setRoundingMode(HALF_UP);
    }

    @Override
    public void writeQuote(Quote quote) {
        StringBuilder outputBuilder =
                new StringBuilder()
                        .append("Requested amount: £")
                        .append(quote.getRequestedAmount())
                        .append(System.lineSeparator())
                        .append("Rate: ")
                        .append(PERCENTAGE_FORMATTER.format(quote.getTotalRate()))
                        .append(System.lineSeparator())
                        .append("Monthly repayment: ")
                        .append(AMOUNT_FORMATTER.format(quote.getMonthlyRepayment()))
                        .append(System.lineSeparator())
                        .append("Total repayment: ")
                        .append(AMOUNT_FORMATTER.format(quote.getTotalRepayment()))
                        .append(System.lineSeparator());

        System.out.println(outputBuilder.toString());
    }

    @Override
    public void writeInsufficientFunding(int leftAmount) {
        String message = String.format("Insufficient offers to cover the loan. Missing £%d", leftAmount);
        System.out.println(message);
    }
}
