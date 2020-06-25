package com.example.currencyconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.db.dao.CurrencyDao
import com.example.currencyconverter.data.db.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

}