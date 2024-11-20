package com.shiyo.featurecurrency.data.di

import com.shiyo.featurecurrency.data.repository.CurrencyRepository
import com.shiyo.featurecurrency.domain.CurrencyUseCaseImpl
import com.shiyo.featurecurrency.ui.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val currencyListModule = module {
    viewModelOf(::CurrencyListViewModel)
    factoryOf(::CurrencyUseCaseImpl) { bind<CurrencyRepository>() }
}