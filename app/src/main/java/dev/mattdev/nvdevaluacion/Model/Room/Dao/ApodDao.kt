package dev.mattdev.nvdevaluacion.Model.Room.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.mattdev.nvdevaluacion.Model.Entity.Apod

@Dao
interface ApodDao {
    @Query("SELECT * FROM apod")
    suspend fun getApods(): List<Apod>

    @Query("SELECT * FROM apod WHERE date > :last ORDER BY date LIMIT :page")
    suspend fun getApodsPagin(last: String, page: Int): List<Apod>

    @Insert
    suspend fun insertAll(apods: List<Apod>)

    @Delete
    suspend fun delete(apod: Apod)

    @Query("DELETE FROM apod")
    suspend fun deleteAll()
}