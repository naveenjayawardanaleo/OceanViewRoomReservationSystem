package com.mycompany.oceanviewresort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillServiceTest {
    @ParameterizedTest
    @CsvSource({
            "0, 0.0",
            "1, 10.0",
            "5, 50.0",
            "12, 120.0"
    })
    void calculateReturnsUnitRateTimesUnits(int units, double expected) {
        BillService service = new BillService();

        double result = service.calculate(units);

        assertEquals(expected, result, 0.0001);
    }
}
