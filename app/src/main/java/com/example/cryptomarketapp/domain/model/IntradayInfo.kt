package com.example.cryptomarketapp.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date : LocalDateTime,
    val close: Double
)
