package com.robertomagale.ratecalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.robertomagale.ratecalculator.calculator.MonthlyPaymentCalculator;
import com.robertomagale.ratecalculator.model.Offer;
import com.robertomagale.ratecalculator.model.Quote;
import com.robertomagale.ratecalculator.reader.DataReader;
import com.robertomagale.ratecalculator.writer.QuoteWriter;
import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class LoanQuoteEngineTest {

    private static final int LOAN_AMOUNT = 500;
    private static final int INVALID_LOAN_AMOUNT = 5000;
    private static final int NUMBER_OF_TERMS = 15;

    @Mock
    private DataReader dataReader;
    @Mock
    private QuoteWriter quoteWriter;
    @Mock
    private MonthlyPaymentCalculator calculator;

    @Captor
    private ArgumentCaptor<Quote> quoteArgumentCaptor;

    private LoanQuoteEngine loanQuoteEngine;

    @Before
    public void setUp() {
        Offer bobOffer = Offer.builder()
                .annualInterestRate(0.075)
                .amount(640)
                .name("Bob")
                .build();

        Offer janeOffer = Offer.builder()
                .annualInterestRate(0.069)
                .amount(480)
                .name("Jane")
                .build();

        Set<Offer> availableOffers = new HashSet<>();
        availableOffers.add(bobOffer);
        availableOffers.add(janeOffer);
        when(dataReader.getOffers()).thenReturn(availableOffers);
        when(calculator.calculate(anyInt(), eq(NUMBER_OF_TERMS), anyDouble())).thenReturn(123D);
        when(calculator.calculate(anyInt(), eq(NUMBER_OF_TERMS), anyDouble())).thenReturn(123D);

        loanQuoteEngine = new LoanQuoteEngine(dataReader, quoteWriter, calculator);
    }

    @Test
    public void shouldThrowExceptionIfDataReaderIsNull() {
        assertThatThrownBy(() -> new LoanQuoteEngine(null, quoteWriter, calculator))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Data reader cannot be null");
    }

    @Test
    public void shouldThrowExceptionIfQuoteWriterIsNull() {
        assertThatThrownBy(() -> new LoanQuoteEngine(dataReader, null, calculator))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Quote writer cannot be null");
    }

    @Test
    public void shouldThrowExceptionIfCalculatorIsNull() {
        assertThatThrownBy(() -> new LoanQuoteEngine(dataReader, quoteWriter, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Monthly payment calculator cannot be null");
    }

    @Test
    public void shouldFindTheBestQuote() {
        loanQuoteEngine.process(LOAN_AMOUNT, NUMBER_OF_TERMS);

        verify(quoteWriter).writeQuote(quoteArgumentCaptor.capture());

        Quote value = quoteArgumentCaptor.getValue();
        assertThat(value.getMonthlyRepayment()).isEqualTo(246.0);
        assertThat(value.getRequestedAmount()).isEqualTo(LOAN_AMOUNT);
        assertThat(value.getTotalRate()).isCloseTo(0.072, Percentage.withPercentage(0.0001));
        assertThat(value.getTotalRepayment()).isEqualTo(3690.0);

        verifyNoMoreInteractions(quoteWriter);
    }

    @Test
    public void shouldNotifyIfOffersCannotCoverLoan() {
        loanQuoteEngine.process(INVALID_LOAN_AMOUNT, NUMBER_OF_TERMS);

        verify(quoteWriter).writeInsufficientFunding(3880);
    }
}