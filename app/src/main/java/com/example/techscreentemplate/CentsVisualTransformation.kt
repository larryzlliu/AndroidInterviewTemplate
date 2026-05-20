package com.example.techscreentemplate

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale
import kotlin.math.pow

class CentsVisualTransformation(private val currency: Currency) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val formatted = formatAmount(digits, currency)
        return TransformedText(AnnotatedString(formatted), AlwaysEndOffsetMapping(digits, formatted))
    }
}

fun formatAmount(digits: String, currency: Currency): String {
    val units = digits.toLongOrNull() ?: 0L
    return if (currency.decimalPlaces == 0) {
        "${currency.symbol}${String.format(Locale.US, "%,d", units)}"
    } else {
        val divisor = 10.0.pow(currency.decimalPlaces).toLong()
        val major = units / divisor
        val minor = units % divisor
        "${currency.symbol}${String.format(Locale.US, "%,d", major)}.${String.format("%0${currency.decimalPlaces}d", minor)}"
    }
}

// Currency fields fill right-to-left like a calculator, so the cursor always lives at the end.
private class AlwaysEndOffsetMapping(
    private val original: String,
    private val transformed: String,
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = transformed.length
    override fun transformedToOriginal(offset: Int): Int = original.length
}
