package com.example.wz_tracker.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wz_tracker.R
import com.example.wz_tracker.databinding.FragmentUsernameBinding
import com.example.wz_tracker.viewModels.UsernameViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsernameFragment : Fragment() {

    private lateinit var binding: FragmentUsernameBinding

    private val viewModel: UsernameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_username, container, false)

        initOnClickListeners()

        return binding.root
    }

    private fun initOnClickListeners() = binding.apply {
        fragmentUsernameBtnSearch.setOnClickListener { handleSavingUser() }
        fragmentUsernameBtnGoToCodWebsite.setOnClickListener { goToCodWebsite() }
    }

    private fun handleSavingUser() =
        binding.fragmentUsernameEtInputActivisionId.text.toString().let { activisionId ->
            checkActivisionId(activisionId)
        }

    private fun checkActivisionId(activisionId: String) =
        viewModel.checkActivisionId(activisionId).let { isGood ->
            when {
                isGood -> saveUser()
                else -> displayErrorMessage()
            }
        }

    private fun saveUser() {
        saveToSharedPreferences()
        showUserSavedToast()
    }

    private fun saveToSharedPreferences() {
        requireActivity().getPreferences(Context.MODE_PRIVATE)?.let { sharedPreferences ->
            with(sharedPreferences.edit()) {
                putString("Username", viewModel.username)
                putString("Numbers", viewModel.numbers)
                apply()
            }
        }
    }

    private fun showUserSavedToast() =
        Toast.makeText(context, "User saved", Toast.LENGTH_SHORT).show()

    private fun displayErrorMessage() {
        binding.fragmentUsernameTextInputLayout.error = "Incorrect username"
    }

    private fun goToCodWebsite() = startActivity(intentToGoCodWebsite())

    private fun intentToGoCodWebsite() =
        Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://profile.callofduty.com/cod/profile"))

}