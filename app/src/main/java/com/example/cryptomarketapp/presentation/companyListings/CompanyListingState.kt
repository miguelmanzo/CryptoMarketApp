package com.example.cryptomarketapp.presentation.companyListings

import com.example.cryptomarketapp.domain.model.CompanyListing

data class CompanyListingState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery : String = ""
)
