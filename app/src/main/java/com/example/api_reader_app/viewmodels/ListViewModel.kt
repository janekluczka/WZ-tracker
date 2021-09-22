package com.example.api_reader_app.viewmodels

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.api_reader_app.repositories.ForecastRepositoryImpl
import kotlinx.coroutines.*
import java.util.*

@SuppressLint("SimpleDateFormat")
class ListViewModel(
    private val repository: ForecastRepositoryImpl
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob)
    var daysList = mutableListOf<String>()
    var numberOfDays = 7

    init {
        Log.i("ListViewModel", "ListViewModel created")

        updateDaysList()
        getData()
    }

    fun updateDaysList() {
        Log.i("ListViewModel", "updateDaysList called")

        val date = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy EEEE")

        daysList.clear()
        for (i in 0..numberOfDays) {
            daysList.add(formatter.format(date.time))
            date.add(Calendar.DATE, 1)
        }
    }

    override fun onCleared() {
        Log.i("ListViewModel", "onCleared called")

        super.onCleared()
        viewModelJob.cancel()
    }


    fun getData() {
        Log.i("ListViewModel", "getData called")

        uiScope.launch {
            repository.getAndSaveDailyForecast()
        }
    }

}