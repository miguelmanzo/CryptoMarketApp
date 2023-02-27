package com.example.cryptomarketapp.presentation.companyListings

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.cryptomarketapp.domain.model.CryptoListings
import java.text.DecimalFormat

//@Preview(
//    "name" ,
//"coinGeckoId" ,
//1 ,
//2 ,
//1 ,
//"high24h" ,
//1f ,
//true,
//)
@Composable
fun CompanyItem(
    company: CryptoListings,
    modifier: Modifier = Modifier,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = modifier.padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = company.image),
                contentDescription = "Forest Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = company.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = company.symbol,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "$${company.currentPrice}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(2.dp))

                val number = company.priceChangePercentage24
                val formattedNumber = DecimalFormat("#,###.##").format(number).toString()


                Text(
                    text = "$formattedNumber%",
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.End),
                    color = if (number >= 0) {
                        MaterialTheme.colors.onSecondary
                    } else {
                        Color.Red
                    }
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val isFavorite =  viewModel.state.favorites.any{ it.name == company.symbol}
                var tint = if (!isFavorite) {
                    Color.LightGray
                } else {
                    Color.Yellow
                }

                Icon(
                    Icons.Outlined.Star,
                    "",
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            viewModel.onEvent(CompanyListingsEvent.OnFavoriteSelection( company.symbol, !isFavorite))
                            tint = Color.Yellow
                        },
                    tint = tint
                )
            }
        }
    }
}

