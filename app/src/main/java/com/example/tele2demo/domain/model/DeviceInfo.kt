package com.example.tele2demo.domain.model

data class DeviceInfo(
    val title: String,
    val subTitle: String,
    val banner: Banner,
    val price: Price,
    val tariffName: String,
    val installmentPlan: InstallmentPlan,
    val characteristic: List<Characteristic>,
    val tariff: List<Tariff>,
    val shopUrls: List<Shop>
) {
    data class Banner(
        val imageUrl: String,
        val videoUrl: String
    )

    data class Price(
        val old: String,
        val new: String
    )

    data class InstallmentPlan(
        val price: String,
        val months: String
    )

    data class Characteristic(
        val iconUrl: String,
        val text: TextLang,
        val value: String
    )

    data class Tariff(
        val iconUrl: String,
        val name: TextLang,
    ) {
    }

    data class Shop(
        val name: String,
        val iconUrl: String,
        val link: String
    )
}