package dev.mattdev.nvdevaluacion.nasa

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import dev.mattdev.nvdevaluacion.Model.Room.NasaDBSingleton
import dev.mattdev.nvdevaluacion.PagListener
import dev.mattdev.nvdevaluacion.R
import dev.mattdev.nvdevaluacion.WorkerNotif
import dev.mattdev.nvdevaluacion.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val viewModel: ApodViewModel by viewModels()
    private val viewModelDb: ApodDBViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var adapter: ApodAdapter? = null
    private var isLoad = false
    private var isLast = false
    private var init = false
    private lateinit var connectivityMan: ConnectivityManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectivityMan = getSystemService(ConnectivityManager::class.java)
        adapter = ApodAdapter( loadImage =  { url, imageV ->
            Glide.with(this)
                .load(url)
                .into(imageV)
        }, openDetail = { apod, imageV->
            startActivity(
                Intent(this, ApodDetailActivity::class.java)
                    .putExtra(ApodDetailActivity.APOD, apod),
                ActivityOptions.makeSceneTransitionAnimation(
                    this, imageV, ApodDetailActivity.IMAGE
                ).toBundle()
            )
        })
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerMain.layoutManager = layoutManager
        binding.recyclerMain.adapter = adapter
        binding.recyclerMain.addOnScrollListener(object : PagListener(layoutManager) {
            override fun loadMoreItems() {
                isLoad = true
                if(getConnect()) {
                    viewModel.getApodsRest()
                } else {
                    if(adapter != null) {
                        viewModelDb.getApodPagin(
                            NasaDBSingleton.db(this@MainActivity),
                            adapter!!.apods[adapter!!.apods.size-1].date
                        )
                    }
                }
            }

            override val isLastPage: Boolean
                get() = isLast
            override val isLoading: Boolean
                get() = isLoad

        })
        checkConnectCallback()
    }

    override fun onResume() {
        super.onResume()
        initObservers()
        initDbObservers()
        if(getConnect()) {
            viewModelDb.getDeleteAll(NasaDBSingleton.db(this))
            viewModel.getApodsRest()
        } else {
            init = false
            viewModelDb.getApodPagin(NasaDBSingleton.db(this))
        }

    }

    override fun onPause() {
        super.onPause()
        removeObservers()
        removeDbObservers()
    }

    private fun initObservers() {
        viewModel.apodsLiveData.observe(this, {
            val indexInit = adapter?.apods?.size ?: 0
            adapter?.apods?.addAll(it)
            adapter?.notifyItemRangeInserted(indexInit, indexInit + it.size)
            isLoad = false
            if(it.size==0){
                isLast = true
            }
            viewModelDb.addAllApod(NasaDBSingleton.db(this), it)

        })
        viewModel.apodsErrorLiveData.observe(this, {
            //Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoading.observe(this, {

        })
    }
    private fun removeObservers() {
        viewModel.apodsLiveData.removeObservers(this)
        viewModel.apodsErrorLiveData.removeObservers(this)
        viewModel.isLoading.removeObservers(this)
    }
    private fun initDbObservers() {
        viewModelDb.apodsDbLiveData.observe(this, {
            val indexInit = adapter?.apods?.size ?: 0
            adapter?.apods?.addAll(it)
            adapter?.notifyItemRangeInserted(indexInit, indexInit + it.size)
            isLoad = false
            if(it.isEmpty()){
                isLast = true
            }
            Log.e("DatosDb", it.toString())

        })
        viewModelDb.apodsDbErrorLiveData.observe(this, {
            Log.e("ErrorDb", it.toString())
            //Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModelDb.apodsDbAddSuccess.observe(this, {
            Log.e("AddDb", "Success")
        })
        viewModelDb.apodsDbDeleteSuccess.observe(this,  {
            Log.e("DeleteDb", "Success")
        })
    }
    private fun removeDbObservers() {
        viewModelDb.apodsDbLiveData.removeObservers(this)
        viewModelDb.apodsDbErrorLiveData.removeObservers(this)
        viewModelDb.apodsDbErrorLiveData.removeObservers(this)
        viewModelDb.apodsDbDeleteSuccess.removeObservers(this)
    }
    private fun checkConnectCallback() {
        connectivityMan.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if(init) {
                    runOnUiThread {
                        binding.textConnectMain.visibility = View.VISIBLE
                        binding.textConnectMain.text = getString(R.string.connect)
                        binding.textConnectMain.setBackgroundColor(getColor(R.color.connect))
                        if(adapter != null) {
                            if(adapter!!.apods.size == 0) {
                                viewModel.getApodsRest()
                            }
                        }
                    }
                    timeTextConnect()
                }
                init = true
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    binding.textConnectMain.visibility = View.VISIBLE
                    binding.textConnectMain.text = getString(R.string.no_connect)
                    binding.textConnectMain.setBackgroundColor(getColor(R.color.no_connect))
                }
                timeTextConnect()
            }
        })
    }
    private fun timeTextConnect() {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    binding.textConnectMain.visibility = View.GONE
                    timer.cancel()
                }
            }
        }
        timer.schedule(timerTask, 1000)
    }
    private fun getConnect(): Boolean {
        val caps = connectivityMan.getNetworkCapabilities(connectivityMan.activeNetwork)
        return caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?:false
    }

    override fun onStop() {
        super.onStop()
        val workReq: WorkRequest = OneTimeWorkRequestBuilder<WorkerNotif>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this)
            .enqueue(workReq)
    }
}