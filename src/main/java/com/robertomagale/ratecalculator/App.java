package com.robertomagale.ratecalculator;

import com.robertomagale.ratecalculator.calculator.CompoundInterestMonthlyPaymentCalculator;
import com.robertomagale.ratecalculator.calculator.MonthlyPaymentCalculator;
import com.robertomagale.ratecalculator.reader.DataReader;
import com.robertomagale.ratecalculator.reader.DataReaderException;
import com.robertomagale.ratecalculator.reader.DataReaderFactory;
import com.robertomagale.ratecalculator.writer.ConsoleQuoteWriter;
import com.robertomagale.ratecalculator.writer.QuoteWriter;

public class App {

    private static final int NUMBER_OF_TERMS = 36;

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage:\n");
            System.out.println("\tjava -jar application.jar [market_file] [loan_amount]");
            System.exit(1);
        }

        String filePath = args[0];
        int loanRequest = Integer.parseInt(args[1]);
        if (loanRequest < 1_000 || loanRequest > 15_000 || loanRequest % 100 != 0) {
            System.out.println("A loan request can only be of any £100 increment between £1000 and £15000 inclusive.");
            System.exit(2);
        }

        QuoteWriter quoteWriter = new ConsoleQuoteWriter();
        MonthlyPaymentCalculator calculator = new CompoundInterestMonthlyPaymentCalculator();
        DataReader dataReader = new DataReaderFactory().getReader(filePath);

        try {
            new LoanQuoteEngine(dataReader, quoteWriter, calculator).process(loanRequest, NUMBER_OF_TERMS);
        } catch (DataReaderException exception) {
            System.out.println("Failed to read input file " + filePath);
        }
    }
}

