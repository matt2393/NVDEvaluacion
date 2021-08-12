package dev.mattdev.nvdevaluacion.nasa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.ResultNasa
import kotlinx.coroutines.launch

class ApodViewModel(private val apodRepository: ApodRepository = ApodRepository()): ViewModel() {
    val apodsLiveData = MutableLiveData<ArrayList<Apod>>()
    val apodsErrorLiveData = MutableLiveData<Throwable>()
    val isLoading = MutableLiveData<Boolean>()

    fun getApodsRest() {
        viewModelScope.launch {
            isLoading.value = true
            val res = apodRepository.getApodsRest()
            isLoading.value = false
            when(res) {
                is ResultNasa.Success -> apodsLiveData.value = res.data!!
                is ResultNasa.Error -> apodsErrorLiveData.value = res.error
            }
        }
    }

}