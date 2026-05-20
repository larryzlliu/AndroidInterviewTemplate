package com.example.techscreentemplate

enum class Currency(val symbol: String, val label: String, val decimalPlaces: Int, val maxUnits: Long) {
    USD("$", "USD ($)", decimalPlaces = 2, maxUnits = 99_999_999L),  // cents: $999,999.99
    JPY("¥", "JPY (¥)", decimalPlaces = 0, maxUnits = 9_999_999L),  // yen: ¥9,999,999
}
