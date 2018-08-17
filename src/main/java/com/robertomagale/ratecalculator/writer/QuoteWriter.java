package com.robertomagale.ratecalculator.writer;

import com.robertomagale.ratecalculator.model.Quote;

public interface QuoteWriter {

    void writeQuote(Quote quote);

    void writeInsufficientFunding(int amount);
}
