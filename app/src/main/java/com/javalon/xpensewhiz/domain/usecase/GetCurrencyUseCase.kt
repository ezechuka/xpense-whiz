package com.javalon.xpensewhiz.domain.usecase

import com.javalon.xpensewhiz.domain.model.CurrencyModel
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor() {
    operator fun invoke(): List<CurrencyModel> {
        val currencies = mutableListOf<CurrencyModel>()
        val countries = mutableListOf<String>()
        val allLocale = Locale.getAvailableLocales()

        allLocale.forEach { locale ->
            val countryName = locale.displayCountry
            try {
                val currencyCode = Currency.getInstance(locale).currencyCode
                val currency = Currency.getInstance(currencyCode)
                val currencySymbol = currency.getSymbol(locale)

                val currencyModel = CurrencyModel(countryName, currencyCode, currencySymbol)
                if (countryName.trim().isNotEmpty() && !countries.contains(countryName)) {
                    countries.add(countryName)
                    currencies.add(currencyModel)
                }
            } catch (e: Exception) { }
        }
        return currencies.sortedBy { it.country }
    }
}