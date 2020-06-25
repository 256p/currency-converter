package com.example.currencyconverter.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconverter.data.db.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyEntity LIMIT 1")
    fun getCurrency() : CurrencyEntity?

    @Insert
    fun saveCurrency(currencyDao: CurrencyEntity)

    @Delete
    fun delete(currencyDao: CurrencyEntity)
}