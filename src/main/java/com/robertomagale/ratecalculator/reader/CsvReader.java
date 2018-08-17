package com.robertomagale.ratecalculator.reader;

import static java.util.Objects.requireNonNull;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.robertomagale.ratecalculator.model.Offer;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CsvReader implements DataReader {

    private static final int NAME_CSV_POSITION = 0;
    private static final int RATE_CSV_POSITION = 1;
    private static final int AMOUNT_CSV_POSITION = 2;
    private String filePath;

    CsvReader(String filePath) {
        this.filePath = requireNonNull(filePath, "File path cannot be null");
    }

    @Override
    public Set<Offer> getOffers() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            Set<Offer> offers = new HashSet<>();

            String[] nextRecord;
            int line = 1;
            while ((nextRecord = csvReader.readNext()) != null) {
                String name = requireNonNull(nextRecord[NAME_CSV_POSITION], "Name cannot be null at line " + line);
                String rate = requireNonNull(nextRecord[RATE_CSV_POSITION], "Rate cannot be null at line " + line);
                String amount = requireNonNull(nextRecord[AMOUNT_CSV_POSITION], "Amount cannot be null at line " + line);
                Offer offer = Offer.builder()
                        .name(name)
                        .annualInterestRate(Double.parseDouble(rate))
                        .amount(Integer.parseInt(amount))
                        .build();

                offers.add(offer);
                line++;
            }

            return Collections.unmodifiableSet(offers);
        } catch (Exception exception) {
            throw new DataReaderException("Failed to read CSV file", exception);
        }
    }
}
