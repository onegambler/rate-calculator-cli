package com.robertomagale.ratecalculator.reader;

import static java.util.Objects.nonNull;

public class DataReaderFactory {

    public DataReader getReader(String input) {
        if (nonNull(input) && input.endsWith(".csv")) {
            return new CsvReader(input);
        }

        throw new DataReaderException("Unable to read input. Currently only CSV files are supported");
    }
}
