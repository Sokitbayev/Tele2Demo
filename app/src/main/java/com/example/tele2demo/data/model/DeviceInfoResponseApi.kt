package com.example.tele2demo.data.model

import com.example.tele2demo.domain.model.TextLang
import com.google.gson.annotations.SerializedName

data class DeviceInfoResponseApi(
    val title: String,
    val tariffName: String?,
    @SerializedName("subtitle")
    val subTitle: String,
    val banner: BannerResponseApi,
    val price: PriceResponseApi,
    val installmentPlan: InstallmentPlanResponseApi,
    val characteristics: List<CharacteristicResponseApi>,
    val tariffResources: List<TariffResponseApi>,
    val shopList: List<ShopResponseApi>
) {
    data class BannerResponseApi(
        val imageUrl: String,
        val videoUrl: String
    )

    data class PriceResponseApi(
        val old: String,
        val new: String
    )

    data class InstallmentPlanResponseApi(
        val price: String,
        val monthsCount: String
    )

    data class CharacteristicResponseApi(
        val iconUrl: String,
        val text: TextLang,
        val value: String
    )

    data class TariffResponseApi(
        val iconUrl: String,
        val text: TextLang,
    )

    data class ShopResponseApi(
        val name: String,
        val imageUrl: String,
        val link: String
    )
}
