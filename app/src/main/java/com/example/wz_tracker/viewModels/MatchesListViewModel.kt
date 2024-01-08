package com.example.wz_tracker.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.repositories.WzRepositoryImpl
import kotlinx.coroutines.*

class MatchesListViewModel(private val repository: WzRepositoryImpl) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob)

    var username = "Dzbaniu"
    var numbers = "9503575"

    val matchesLiveData: MutableLiveData<List<Match>> = MutableLiveData()

    fun fetchData() =
        uiScope.launch {
            val matches = repository.fetchWzMatches(username, numbers)
            matchesLiveData.postValue(matches)
        }

    fun setUsernameAndNumbers(username: String, numbers: String) {
        this.username = username
        this.numbers = numbers
    }

    fun noUserSet(): Boolean = username.isEmpty() && numbers.isEmpty()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}