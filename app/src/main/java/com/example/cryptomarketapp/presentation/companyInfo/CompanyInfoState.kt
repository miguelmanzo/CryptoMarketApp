package com.example.cryptomarketapp.presentation.companyInfo

import com.example.cryptomarketapp.domain.model.CompanyInfo
import com.example.cryptomarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
