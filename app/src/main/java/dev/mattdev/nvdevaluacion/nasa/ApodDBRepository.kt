package dev.mattdev.nvdevaluacion.nasa

import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.Model.Room.NasaDBSingleton
import dev.mattdev.nvdevaluacion.Model.Room.NasaDatabase
import dev.mattdev.nvdevaluacion.ResultNasa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodDBRepository {
    suspend fun addAllApod(nasaDatabase: NasaDatabase, apods: List<Apod>): ResultNasa<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val a = nasaDatabase.apodDao().insertAll(apods)
                ResultNasa.Success(a)
            } catch (ex: Throwable) {
                ResultNasa.Error(ex)
            }
        }
    }
    suspend fun getApodPagin(nasaDatabase: NasaDatabase, last: String): ResultNasa<List<Apod>> {
        return withContext(Dispatchers.IO) {
            try {
                val apods = nasaDatabase.apodDao().getApodsPagin(last, 10)
                ResultNasa.Success(apods)
            } catch (ex: Throwable) {
                ResultNasa.Error(ex)
            }
        }
    }
    suspend fun deleteAll(nasaDatabase: NasaDatabase): ResultNasa<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val a = nasaDatabase.apodDao().deleteAll()
                ResultNasa.Success(a)
            } catch (ex: Throwable) {
                ResultNasa.Error(ex)
            }
        }
    }
}