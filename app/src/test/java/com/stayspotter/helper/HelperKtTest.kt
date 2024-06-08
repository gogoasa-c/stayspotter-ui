package com.stayspotter.helper


import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.util.Calendar

class HelperKtTest {
    @Test
    fun testConvertEpochToDate() {
        assertEquals("2024-04-11", convertEpochToDate(1712865430338L))
    }

    @Test
    fun testConvertStringToCalendar() {
        val calendar = convertStringToCalendar("2024-04-11")
        assertEquals(2024, calendar.get(Calendar.YEAR))
        assertEquals(3, calendar.get(Calendar.MONTH))
        assertEquals(11, calendar.get(Calendar.DAY_OF_MONTH))
    }
}