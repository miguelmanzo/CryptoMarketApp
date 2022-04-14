package com.example.cryptomarketapp.di

import com.example.cryptomarketapp.data.csv.CSVParser
import com.example.cryptomarketapp.data.csv.CompanyListingParser
import com.example.cryptomarketapp.data.csv.IntradayInfoParser
import com.example.cryptomarketapp.data.repository.StockRepositoryImpl
import com.example.cryptomarketapp.domain.model.CompanyInfo
import com.example.cryptomarketapp.domain.model.CompanyListing
import com.example.cryptomarketapp.domain.model.IntradayInfo
import com.example.cryptomarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCompanyListingsParser(
     companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    abstract fun bindIntradayInfoParser(
     intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ) : StockRepository
}