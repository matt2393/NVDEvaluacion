package dev.mattdev.nvdevaluacion

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.mattdev.nvdevaluacion.nasa.ApodViewModel
import junit.framework.TestCase
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApodsRestViewModelTest: TestCase() {
    private lateinit var viewModel: ApodViewModel
    @Before
    override fun setUp() {
        viewModel = ApodViewModel()
    }

    @Test
    fun getApodsRestTest() {
        viewModel.getApodsRest()
        val apods = viewModel.apodsLiveData.value
        assertThat(apods, `is`(viewModel))
    }
}