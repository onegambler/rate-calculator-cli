package com.robertomagale.ratecalculator.writer;

import static org.assertj.core.api.Assertions.assertThat;

import com.robertomagale.ratecalculator.model.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleQuoteWriterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final ConsoleQuoteWriter writer = new ConsoleQuoteWriter();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void teardown() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldProperlyPrintAQuote() {
        Quote testQuote = Quote.builder()
                .totalRepayment(10)
                .monthlyRepayment(20)
                .totalRate(5)
                .requestedAmount(400)
                .build();


        writer.writeQuote(testQuote);

        String expectedOutput = "Requested amount: £400\n" +
                "Rate: 500.0%\n" +
                "Monthly repayment: £20.00\n" +
                "Total repayment: £10.00\n" +
                "\n";

        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void shouldProperlyPrintInsufficientFunding() {
        writer.writeInsufficientFunding(500);

        String expectedOutput = "Insufficient offers to cover the loan. Missing £500\n";
        assertThat(outContent.toString()).isEqualTo(expectedOutput);
    }
}