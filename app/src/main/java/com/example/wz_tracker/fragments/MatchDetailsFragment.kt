package com.example.wz_tracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.wz_tracker.R
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.databinding.FragmentMatchDetailsBinding
import com.example.wz_tracker.util.BackgroundUtil
import com.example.wz_tracker.util.DateFormatterUtil
import com.example.wz_tracker.util.KdHelperUtil
import com.example.wz_tracker.util.ModeNameUtil
import com.example.wz_tracker.viewModels.MatchDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class MatchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMatchDetailsBinding
    private val args: MatchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_match_details,
            container,
            false
        )

        val viewModel: MatchDetailsViewModel by viewModel { parametersOf(args.gameID) }

        viewModel.match.observe(viewLifecycleOwner) { match ->
            match?.let {
                setUpLayout(it)
            }
        }

        return binding.root
    }

    private fun setUpLayout(match: Match) {
        setUpDate(match)
        setUpMode(match)
        setUpPlace(match)
        setUpKills(match)
        setUpDeaths(match)
        setUpKD(KdHelperUtil.calculateKd(match.kills, match.deaths), match)
        setUpDamage(match)
    }

    private fun setUpDate(match: Match) {
        binding.fragmentMatchDetailsTvDate.text = DateFormatterUtil.getFormattedDate(match.date)
        binding.fragmentMatchDetailsTvTime.text = DateFormatterUtil.getFormattedTime(match.date)
    }

    private fun setUpMode(match: Match) {
        binding.fragmentMatchDetailsTvModeName.text = ModeNameUtil.getMatchMode(match.mode)
    }

    private fun setUpPlace(match: Match) {
        setPlaceText(match.place)
        setPlaceBackground(match.place)
    }

    private fun setPlaceText(place: Int) {
        binding.fragmentMatchDetailsTvPlaceNumber.text = place.toString()
    }

    private fun setPlaceBackground(id: Int) {
        val drawable = BackgroundUtil.getMatchPlaceBackground(id)
        binding.fragmentMatchDetailsClPlace.background = getDrawable(drawable)
    }

    private fun setUpKills(match: Match) {
        binding.fragmentMatchDetailsTvKillsAmount.text = match.kills.toString()
    }

    private fun setUpDeaths(match: Match) {
        binding.fragmentMatchDetailsTvDeathsAmount.text = match.deaths.toString()
    }

    private fun setUpKD(kd: Float, match: Match) {
        setKdText(kd)
        setKdBackground(kd, match)
    }

    private fun setKdText(kd: Float) {
        binding.fragmentMatchDetailsTvKdNumber.text = KdHelperUtil.formatKD(kd)
    }

    private fun setKdBackground(kd: Float, match: Match) {
        val drawable = BackgroundUtil.getMatchKdBackground(kd, match)
        binding.fragmentMatchDetailsClKd.background = getDrawable(drawable)
    }

    private fun setUpDamage(match: Match) {
        binding.fragmentMatchDetailsTvDamageDoneAmount.text = match.damageDone.toString()
        binding.fragmentMatchDetailsTvDamageTakenAmount.text = match.damageTaken.toString()
    }

    private fun getDrawable(drawable: Int) = requireContext().getDrawable(drawable)

}