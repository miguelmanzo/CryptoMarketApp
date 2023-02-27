package com.example.cryptomarketapp.data.local

import androidx.room.*

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT *
            FROM CompanyListingEntity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>

    @Query(
        """
            SELECT *
            FROM CryptoListingEntity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == name
        """
    )
    suspend fun searchCryptoListing(query: String): List<CryptoListingEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptoListings(
        cryptoListingEntity: List<CryptoListingEntity>
    )

    @Query("DELETE FROM cryptolistingentity")
    suspend fun clearCryptoListings()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoritesListingEntity: FavoritesListingEntity) // no need of suspend

    @Query(
        """
            SELECT *
            FROM FavoritesListingEntity
        """
    )
    fun getFavoritesListing(): List<FavoritesListingEntity>

    @Delete
    fun removeFavorite(favoritesListingEntity: FavoritesListingEntity) // no need of suspend
}