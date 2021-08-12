package dev.mattdev.nvdevaluacion

import android.content.Context
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.mattdev.nvdevaluacion.Model.Entity.Apod
import dev.mattdev.nvdevaluacion.Model.Room.NasaDBSingleton
import dev.mattdev.nvdevaluacion.nasa.ApodDBViewModel
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApodsDbViewModelTest: TestCase() {
    private lateinit var viewModel: ApodDBViewModel
    private lateinit var context: Context
    @Before
    override fun setUp() {
        viewModel = ApodDBViewModel()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getApodsDbTest() {
        viewModel.getApodPagin(NasaDBSingleton.db(context))
        val apods = viewModel.apodsDbLiveData.value
        ViewMatchers.assertThat(apods, Matchers.`is`(viewModel))
    }
    @Test
    fun addAllApodsDbTest() {
        val list = listOf(Apod(
            date = "2001-01-01",
            explanation = "Prueba", title = "Titulo"))
        viewModel.addAllApod(NasaDBSingleton.db(context), list)
        val success = viewModel.apodsDbAddSuccess.value
        ViewMatchers.assertThat(success, Matchers.`is`(viewModel))
    }
    @Test
    fun deleteAllApodsDbTest() {
        viewModel.getDeleteAll(NasaDBSingleton.db(context))
        val success = viewModel.apodsDbDeleteSuccess.value
        ViewMatchers.assertThat(success, Matchers.`is`(viewModel))
    }
}