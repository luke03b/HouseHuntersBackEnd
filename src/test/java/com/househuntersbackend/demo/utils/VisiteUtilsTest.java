package com.househuntersbackend.demo.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VisiteUtilsTest {

    @Test
    void testDataValida() {
        assertTrue(VisiteUtils.isVisitaValida(LocalDate.now().plusDays(1), LocalTime.of(9, 0), LocalTime.of(10, 0)));
    }
}
