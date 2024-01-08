package com.example.wz_tracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.wz_tracker.R
import com.example.wz_tracker.databinding.FragmentLifetimeStatsBinding
import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.util.BackgroundUtil
import com.example.wz_tracker.util.KdHelperUtil
import com.example.wz_tracker.viewModels.LifetimeStatsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LifetimeStatsFragment : Fragment() {

    private lateinit var binding: FragmentLifetimeStatsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LifetimeStatsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflateDataBinding(inflater, container)
        sharedPreferences = getSharedPreferences()

        getAndCheckUser()
        setUpViewModel()

        return binding.root
    }

    private fun inflateDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLifetimeStatsBinding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_lifetime_stats,
        container,
        false
    )

    private fun getSharedPreferences() = requireActivity().getPreferences(Context.MODE_PRIVATE)

    private fun getAndCheckUser() {
        viewModel.setUsernameAndNumbers(
            getStringFromSharedPreferences("Username"),
            getStringFromSharedPreferences("Numbers")
        )
        if (viewModel.noUserSet()) navigateToUsernameFragment()
    }

    private fun getStringFromSharedPreferences(key: String, defValue: String = "") =
        sharedPreferences.getString(key, defValue).toString()

    private fun navigateToUsernameFragment() =
        findNavController().navigate(
            LifetimeStatsFragmentDirections.actionLifetimeStatsFragmentToUsernameFragment()
        )

    private fun setUpViewModel() {
        viewModel.fetchData()
        viewModel.lifetimeStatsLiveData.observe(viewLifecycleOwner) { lifetimeStats ->
            updateLayout(lifetimeStats)
        }
    }

    private fun updateLayout(lifetimeStats: LifetimeStats?) {
        hideProgressIndicator()
        if (lifetimeStats != null) setUpLayout(lifetimeStats)
        else showSomethingWentWrongSnackbar()
    }

    private fun hideProgressIndicator() {
        binding.fragmentLifetimeStatsProgressIndicator.visibility = View.GONE
    }

    private fun setUpLayout(lifetimeStats: LifetimeStats) {
        showStats()
        setUpWins(lifetimeStats)
        setUpKills(lifetimeStats)
        setUpDeaths(lifetimeStats)
        setUpKd(lifetimeStats)
    }

    private fun showStats() {
        binding.fragmentLifetimeStatsSvStats.visibility = View.VISIBLE
    }

    private fun setUpWins(lifetimeStats: LifetimeStats) {
        setWinsText(lifetimeStats.br_all.wins)
        setWinsBackground(lifetimeStats.br_all.wins)
    }

    private fun setWinsText(wins: Int) {
        binding.fragmentLifetimeStatsWinsAmount.text = wins.toString()
    }

    private fun setWinsBackground(wins: Int) {
        val drawable = getWinsBackground(wins)
        binding.fragmentLifetimeStatsClWins.background = getDrawableFromResources(drawable)
    }

    private fun getWinsBackground(wins: Int) = BackgroundUtil.getLifetimeWinsBackground(wins)

    private fun setUpKills(lifetimeStats: LifetimeStats) {
        binding.fragmentLifetimeStatsTvKillsAmount.text = lifetimeStats.br_all.kills.toString()
    }

    private fun setUpDeaths(lifetimeStats: LifetimeStats) {
        binding.fragmentLifetimeStatsTvDeathsAmount.text = lifetimeStats.br_all.deaths.toString()
    }

    private fun setUpKd(lifetimeStats: LifetimeStats) {
        val kd = calculateKd(lifetimeStats)
        setKdText(kd)
        setKdBackground(kd)
    }

    private fun calculateKd(lifetimeStats: LifetimeStats) =
        KdHelperUtil.calculateKd(lifetimeStats.br_all.kills, lifetimeStats.br_all.deaths)

    private fun setKdText(kd: Float) {
        binding.fragmentLifetimeStatsTvKdNumber.text = KdHelperUtil.formatKD(kd)
    }

    private fun setKdBackground(kd: Float) {
        val drawable = getKdBackground(kd)
        binding.fragmentLifetimeStatsClKd.background = getDrawableFromResources(drawable)
    }

    private fun getKdBackground(kd: Float) = BackgroundUtil.getLifetimeKdBackground(kd)

    private fun showSomethingWentWrongSnackbar() =
        buildSomethingWentWrongSnackbarWithTryAgainAction().show()

    private fun buildSomethingWentWrongSnackbarWithTryAgainAction() =
        buildSomethingWentWrongSnackbar().setAction(requireContext().getText(R.string.try_again)) {
            viewModel.fetchData()
        }

    private fun buildSomethingWentWrongSnackbar() =
        Snackbar.make(
            binding.fragmentLifetimeStatsCoordinatorLayout,
            requireContext().getText(R.string.something_went_wrong),
            Snackbar.LENGTH_INDEFINITE
        )

    private fun getDrawableFromResources(drawable: Int) =
        AppCompatResources.getDrawable(requireContext(), drawable)

}