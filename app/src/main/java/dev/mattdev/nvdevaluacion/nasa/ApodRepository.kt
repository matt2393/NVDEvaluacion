package dev.mattdev.nvdevaluacion.nasa

import dev.mattdev.nvdevaluacion.BuildConfig
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.Model.Rest.RetrofitSingleton
import dev.mattdev.nvdevaluacion.ResultNasa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodRepository {
    suspend fun getApodsRest(): ResultNasa<ArrayList<Apod>> {
        return withContext(Dispatchers.IO){
            try {
                val apods = RetrofitSingleton.request.getApod(10, BuildConfig.NASA_APIKEY)
                ResultNasa.Success(apods)
            } catch (ex: Exception) {
                ResultNasa.Error(ex)
            }
        }
    }
}