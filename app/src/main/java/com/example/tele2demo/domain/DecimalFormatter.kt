package com.example.tele2demo.domain

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.regex.Pattern

class DecimalFormatter : DecimalFormat("#,###.##") {
    init {
        this.maximumFractionDigits =
            FRACTION_MAX
        this.minimumFractionDigits =
            FRACTION_MIN
        this.isGroupingUsed = true
        this.groupingSize =
            GROUP_SIZE
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator =
            SEPARATOR_DECIMAL
        symbols.groupingSeparator =
            SEPARATOR_GROUP
        this.decimalFormatSymbols = symbols
        this.roundingMode = RoundingMode.DOWN
    }

    fun formatText(cleanText: String): String {
        return if (amountPattern.matcher(cleanText).matches()) cleanText else this.cleanText(
            cleanText
        )
    }

    fun cleanText(formattedText: String): String {
        val decimal = formattedText.toAmountOrDefault(BigDecimal.ZERO)
        return if (decimal == null) "" else this.format(decimal)
    }

    companion object {
        const val FRACTION_MIN = 0
        const val FRACTION_MAX = 2
        const val GROUP_SIZE = 3
        const val SEPARATOR_DECIMAL = ','
        const val SEPARATOR_GROUP = ' '
        const val SEPARATOR_DECIMAL_DEFAULT = "."
        const val DEFAULT_CURRENCY = "₸"

        private val amountPattern: Pattern
            get() {
                val patternBuilder = "^\\d{1,3}( \\d{3})*(\\" + SEPARATOR_DECIMAL + "\\d{0,2})?"
                return Pattern.compile(patternBuilder)
            }
    }
}