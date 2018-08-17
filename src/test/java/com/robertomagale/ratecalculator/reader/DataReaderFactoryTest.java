package com.robertomagale.ratecalculator.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class DataReaderFactoryTest {

    private DataReaderFactory factory = new DataReaderFactory();

    @Test
    public void whenFileIsCsvThenReturnCsvReader() {
        DataReader reader = factory.getReader("something.csv");
        assertThat(reader).isInstanceOf(CsvReader.class);
    }

    @Test
    public void whenFileTypeIsUnknownThenThrowException() {
        assertThatThrownBy(() -> factory.getReader("something.wrong"))
                .isInstanceOf(DataReaderException.class)
                .hasMessage("Unable to read input. Currently only CSV files are supported");
    }
}