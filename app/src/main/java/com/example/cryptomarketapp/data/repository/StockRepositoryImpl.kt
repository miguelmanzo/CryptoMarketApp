package com.example.cryptomarketapp.data.repository

import com.example.cryptomarketapp.data.csv.CSVParser
import com.example.cryptomarketapp.data.local.FavoritesListingEntity
import com.example.cryptomarketapp.data.local.StockDataClass
import com.example.cryptomarketapp.data.mapper.*
import com.example.cryptomarketapp.data.remote.StockApi
import com.example.cryptomarketapp.domain.model.*
import com.example.cryptomarketapp.domain.repository.StockRepository
import com.example.cryptomarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDataClass,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>,
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListing.map { it.toCompanyListing() }))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { value ->
                        value.toCompanyListingEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }

    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load intraday info"
            )
        }
    }


    override suspend fun getCryptoListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CryptoListings>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCryptoListing(query)
            emit(Resource.Success(data = localListing.map { it.toCryptoListing() }))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getCryptosList()
                response.map { it.toCryptoListingEntity() }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCryptoListings()
                dao.insertCryptoListings(
                    listings
                )
                emit(Resource.Success(
                    data = dao.searchCryptoListing("").map { it.toCryptoListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun addCryptoToFavorites(isFavorite: String) {
        dao.insertFavorite(
            FavoritesListingEntity(
                isFavorite
            )
        )
    }

    override suspend fun removeCryptoFromFavorites(isFavorite: String) {
        dao.removeFavorite(
            FavoritesListingEntity(
                isFavorite
            )
        )
    }

    override suspend fun getFavoritesListings(
    ): List<FavoriteListings> {
        return dao.getFavoritesListing().map { it.toFavoriteListing() }
    }


    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load company info"
            )
        }
    }
}