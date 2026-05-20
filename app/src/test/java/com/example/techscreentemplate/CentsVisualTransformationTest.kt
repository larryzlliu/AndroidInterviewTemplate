package com.example.techscreentemplate

import org.junit.Assert.assertEquals
import org.junit.Test

class CentsVisualTransformationTest {

    // USD (cents-based)

    @Test
    fun `USD empty string formats as zero`() {
        assertEquals("\$0.00", formatAmount("", Currency.USD))
    }

    @Test
    fun `USD zero formats as zero`() {
        assertEquals("\$0.00", formatAmount("0", Currency.USD))
    }

    @Test
    fun `USD single digit is cents`() {
        assertEquals("\$0.05", formatAmount("5", Currency.USD))
    }

    @Test
    fun `USD two digits are cents`() {
        assertEquals("\$0.50", formatAmount("50", Currency.USD))
    }

    @Test
    fun `USD three digits splits one dollar and cents`() {
        assertEquals("\$1.23", formatAmount("123", Currency.USD))
    }

    @Test
    fun `USD six digits with no comma`() {
        assertEquals("\$999.99", formatAmount("99999", Currency.USD))
    }

    @Test
    fun `USD seven digits adds thousands comma`() {
        assertEquals("\$12,345.67", formatAmount("1234567", Currency.USD))
    }

    @Test
    fun `USD max amount formats correctly`() {
        assertEquals("\$999,999.99", formatAmount("99999999", Currency.USD))
    }

    @Test
    fun `USD cents padded with leading zero`() {
        assertEquals("\$1.05", formatAmount("105", Currency.USD))
    }

    // JPY (whole-unit)

    @Test
    fun `JPY empty string formats as zero`() {
        assertEquals("¥0", formatAmount("", Currency.JPY))
    }

    @Test
    fun `JPY single digit`() {
        assertEquals("¥5", formatAmount("5", Currency.JPY))
    }

    @Test
    fun `JPY four digits adds thousands comma`() {
        assertEquals("¥1,234", formatAmount("1234", Currency.JPY))
    }

    @Test
    fun `JPY max amount formats correctly`() {
        assertEquals("¥9,999,999", formatAmount("9999999", Currency.JPY))
    }
}
