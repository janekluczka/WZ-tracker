package com.example.wz_tracker.viewModels

import androidx.lifecycle.ViewModel
import java.lang.Integer.parseInt
import java.lang.NumberFormatException

class UsernameViewModel : ViewModel() {

    var username: String = "Dzbaniu"
    var numbers: String = "9503575"

    private var splitActivisionId: List<String> = emptyList()

    companion object {
        const val SPLIT_ACTIVISION_ID_SIZE = 2
        const val NUMBERS_LENGTH = 7
        const val HASH = '#'
    }

    fun checkActivisionId(activisionId: String): Boolean {
        splitActivisionId = activisionId.split(HASH)
        return if (splitActivisionIdIsCorrect()) saveSplitActivisionId()
        else false
    }

    private fun splitActivisionIdIsCorrect(): Boolean =
        when {
            sizeIsIncorrect() -> false
            usernameIsIncorrect() || numbersAreIncorrect() -> false
            else -> true
        }

    private fun sizeIsIncorrect() = splitActivisionId.size != SPLIT_ACTIVISION_ID_SIZE

    private fun usernameIsIncorrect(): Boolean = splitActivisionId[0].isBlank()

    private fun numbersAreIncorrect(): Boolean = splitActivisionId[1].isBlank() ||
                numbersHaveIncorrectLength() ||
                numbersAreNotNumeric()

    private fun numbersHaveIncorrectLength() =
        splitActivisionId[1].length != NUMBERS_LENGTH

    private fun numbersAreNotNumeric(): Boolean =
        try {
            parseInt(splitActivisionId[1])
            false
        } catch (e: NumberFormatException) {
            true
        }

    private fun saveSplitActivisionId(): Boolean {
        username = splitActivisionId[0]
        numbers = splitActivisionId[1]
        return true
    }
}
