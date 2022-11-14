package com.example.tele2demo.domain

import java.math.BigDecimal

fun String?.toAmountOrDefault(def: BigDecimal = BigDecimal.ZERO): BigDecimal {
    return this?.toAmount() ?: def
}

fun String.toAmount(): BigDecimal? {
    var value = this
    var result: BigDecimal? = null

    try {

        value = value
            .trim()
            .replace("\\s", "")
            .replace(',', '.')
            .replace("[^\\+0-9.-]".toRegex(), "")

        result = BigDecimal(value)
    } catch (var3: Exception) {
        var3.printStackTrace()
    }

    return result
}