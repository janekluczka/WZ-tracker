package com.example.wz_tracker.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.repositories.WzRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LifetimeStatsViewModel(private val repository: WzRepositoryImpl) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob)

    var username = "Dzbaniu"
    var numbers = "9503575"

    val lifetimeStatsLiveData: MutableLiveData<LifetimeStats> = MutableLiveData()

    fun fetchData() =
        uiScope.launch {
            val lifetimeStats = repository.fetchWzLifetimeStats(username, numbers)
            lifetimeStatsLiveData.postValue(lifetimeStats)
        }

    fun setUsernameAndNumbers(username: String, numbers: String) {
        this.username = username
        this.numbers = numbers
    }

    fun noUserSet(): Boolean = areUsernameAndNumbersEmpty()

    private fun areUsernameAndNumbersEmpty() = isEmpty(username) && isEmpty(numbers)

    private fun isEmpty(string: String) = string == ""

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}