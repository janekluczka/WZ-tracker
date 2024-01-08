package com.example.wz_tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wz_tracker.R
import com.example.wz_tracker.databinding.FragmentStatsNavigationBinding

class StatsTabsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentStatsNavigationBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_stats_navigation,
            container,
            false
        )

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.fragment_stats_navigation_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.fragmentStatsNavigationBottomNavigation.setupWithNavController(navController)

        return binding.root
    }

}