package com.stayspotter.helper


import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class HelperKtTest {
    @Test
    fun testConvertEpochToDate() {
        assertEquals("11-04-2024", convertEpochToDate(1712865430338L))
    }

}