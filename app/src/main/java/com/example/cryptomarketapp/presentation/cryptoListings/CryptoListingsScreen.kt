package com.example.cryptomarketapp.presentation.cryptoListings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptomarketapp.domain.model.CryptoListings
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CryptoListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CryptoListingsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    Column(
        Modifier.fillMaxSize()
    ) {

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onEvent(CryptoListingsEvent.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .weight(0.8f)
                    .padding(14.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.LightGray),
                maxLines = 1,
                placeholder = { Text(text = "Search") }
            )

            Icon(
                Icons.Outlined.Star,
                "",
                modifier = Modifier
                    .weight(0.2f)
                    .size(44.dp)
                    .padding(end = 8.dp)
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() } // This is mandatory
                    ) {
                        viewModel.onEvent(CryptoListingsEvent.FavoriteCompaniesToggle(!state.favoriteCompaniesFilterOn))
                    },
                tint = if (state.favoriteCompaniesFilterOn) {
                    Color.Yellow
                } else {
                    Color.LightGray
                }
            )
        }

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(CryptoListingsEvent.Refresh) })
        {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                val companies: List<CryptoListings> =
                    if (state.favoriteCompaniesFilterOn) {
                        viewModel.getFavoriteCompanies()
                    } else {
                        state.companies
                    }

                items(companies.size) { i ->

                    CryptoItem(
                        company = companies[i],
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        viewModel
                    )

                    //TODO to refactor
//                            .clickable {
//                                   navigator.navigate(
//                                       CompanyInfoScreenDestination(company.symbol)
//                                   )
//                            })
                }
            }
        }
    }
}