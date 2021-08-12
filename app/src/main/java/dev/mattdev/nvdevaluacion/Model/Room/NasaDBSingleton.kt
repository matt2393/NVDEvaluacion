package dev.mattdev.nvdevaluacion.Model.Room

import android.content.Context
import androidx.room.Room

object NasaDBSingleton {
    fun db(ctx: Context) =
        Room.databaseBuilder(ctx, NasaDatabase::class.java, "nasa-db")
            .build()
}