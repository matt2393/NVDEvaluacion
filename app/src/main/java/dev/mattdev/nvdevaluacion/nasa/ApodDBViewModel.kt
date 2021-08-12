package dev.mattdev.nvdevaluacion.nasa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.Model.Room.NasaDatabase
import dev.mattdev.nvdevaluacion.ResultNasa
import kotlinx.coroutines.launch

class ApodDBViewModel(private val apodDBRepository: ApodDBRepository = ApodDBRepository()): ViewModel() {
    val apodsDbLiveData = MutableLiveData<List<Apod>>()
    val apodsDbErrorLiveData = MutableLiveData<Throwable>()
    val apodsDbAddSuccess = MutableLiveData<Unit>()
    val apodsDbDeleteSuccess = MutableLiveData<Unit>()
    fun addAllApod(nasaDatabase: NasaDatabase, apods: List<Apod>) {
        viewModelScope.launch {
            val res = apodDBRepository.addAllApod(nasaDatabase, apods)
            when(res) {
                is ResultNasa.Success -> apodsDbAddSuccess.value = res.data!!
                is ResultNasa.Error -> apodsDbErrorLiveData.value = res.error
            }
        }
    }
    fun getApodPagin(nasaDatabase: NasaDatabase, last: String = "1900-01-01") {
        viewModelScope.launch {
            val res = apodDBRepository.getApodPagin(nasaDatabase, last)
            when(res) {
                is ResultNasa.Success -> apodsDbLiveData.value = res.data!!
                is ResultNasa.Error -> apodsDbErrorLiveData.value = res.error
            }
        }
    }
    fun getDeleteAll(nasaDatabase: NasaDatabase) {
        viewModelScope.launch {
            val res = apodDBRepository.deleteAll(nasaDatabase)
            when(res) {
                is ResultNasa.Success -> apodsDbDeleteSuccess.value = res.data!!
                is ResultNasa.Error -> apodsDbErrorLiveData.value = res.error
            }
        }
    }
}