package com.example.wz_tracker.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.repositories.WzRepositoryImpl
import kotlinx.coroutines.*

class MatchDetailsViewModel(
    private val matchId: Int,
    private val repository: WzRepositoryImpl
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob)

    val match = MutableLiveData<Match?>()

    init {
        getMatch()
    }

    private fun getMatch() = uiScope.launch {
        repository.getWzMatchByID(matchId).let {
            match.postValue(it)
        }
    }

}