package com.example.cryptomarketapp.presentation.companyListings

sealed class CompanyListingsEvent{
    object  Refresh:  CompanyListingsEvent()
    data class  OnSearchQueryChange(val query: String): CompanyListingsEvent()
}
