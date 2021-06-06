package com.fkocak.witranslate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fkocak.witranslate.GenreConverters
import com.fkocak.witranslate.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}