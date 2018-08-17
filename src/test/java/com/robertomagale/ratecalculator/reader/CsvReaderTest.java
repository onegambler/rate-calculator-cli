package com.robertomagale.ratecalculator.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.robertomagale.ratecalculator.model.Offer;
import com.robertomagale.ratecalculator.util.FixtureHelper;
import org.junit.Test;

import java.util.Set;

public class CsvReaderTest extends FixtureHelper {

    @Test
    public void shouldCorrectlyLoadACSVFile() {
        CsvReader reader = new CsvReader(getResourcePath("data.csv"));
        Set<Offer> expectedOffers = reader.getOffers();

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

        assertThat(expectedOffers).containsOnly(bobOffer, janeOffer);
    }

    @Test
    public void shouldThrowExceptionIfFilePathIsNull() {
        assertThatThrownBy(() -> new CsvReader(null))
                .isInstanceOf(NullPointerException.class);
    }
}