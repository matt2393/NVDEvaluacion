package dev.mattdev.nvdevaluacion.Model.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.Model.Room.Dao.ApodDao

@Database(entities = [Apod::class], version = 1)
abstract class NasaDatabase: RoomDatabase() {
    abstract fun apodDao(): ApodDao
}