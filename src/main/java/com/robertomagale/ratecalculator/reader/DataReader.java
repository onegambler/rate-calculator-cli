package com.robertomagale.ratecalculator.reader;

import com.robertomagale.ratecalculator.model.Offer;

import java.util.Set;

public interface DataReader {

    Set<Offer> getOffers();
}
