package com.example.tele2demo.data

import com.example.tele2demo.data.model.DeviceInfoResponseApi
import com.example.tele2demo.domain.DecimalFormatter
import com.example.tele2demo.domain.Mapper
import com.example.tele2demo.domain.model.DeviceInfo

class DeviceInfoMapper : Mapper<DeviceInfoResponseApi, DeviceInfo>() {

    private val decimalFormatter = DecimalFormatter()

    override fun map(from: DeviceInfoResponseApi): DeviceInfo {
        return DeviceInfo(
            title = from.title,
            tariffName = from.tariffName ?: "",
            subTitle = from.subTitle,
            banner = DeviceInfo.Banner(from.banner.imageUrl, from.banner.videoUrl),
            price = DeviceInfo.Price(
                decimalFormatter.formatText(from.price.old) + " ₸",
                decimalFormatter.formatText(from.price.new) + " ₸"
            ),
            installmentPlan = DeviceInfo.InstallmentPlan(
                decimalFormatter.formatText(from.installmentPlan.price) + " ₸",
                "X" + from.installmentPlan.monthsCount
            ),
            characteristic = from.characteristics.map {
                DeviceInfo.Characteristic(
                    it.iconUrl,
                    it.text,
                    it.value
                )
            },
            tariff = from.tariffResources.map { DeviceInfo.Tariff(it.iconUrl, it.text) },
            shopUrls = from.shopList.map { DeviceInfo.Shop(it.name, it.imageUrl, it.link) }
        )
    }
}