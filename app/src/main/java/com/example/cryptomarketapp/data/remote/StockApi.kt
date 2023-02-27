package com.example.cryptomarketapp.data.remote

import com.example.cryptomarketapp.data.remote.dto.CompanyInfoDto
import com.example.cryptomarketapp.data.remote.dto.CryptoListingResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    companion object {
        const val API_KEY = "UUN70AG20EKSAFEE"
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        const val CURRENCY = "usd"
        const val CYRYPTOS_PER_PAGE = 1000
    }

    // TODO: Implement later
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyInfoDto

    @GET("coins/markets?")
    suspend fun getCryptosList(
        @Query("vs_currency") vsCurrency: String = CURRENCY,
        @Query("per_page") perPage: Int = CYRYPTOS_PER_PAGE
    ): List<CryptoListingResponse>
}