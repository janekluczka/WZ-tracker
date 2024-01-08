package com.example.wz_tracker.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wz_tracker.R
import com.example.wz_tracker.adapters.MatchesListAdapter
import com.example.wz_tracker.database.Match
import com.example.wz_tracker.databinding.FragmentMatchesListBinding
import com.example.wz_tracker.viewModels.MatchesListViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchesListFragment : Fragment() {

    private lateinit var binding: FragmentMatchesListBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: MatchesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_matches_list,
            container,
            false
        )

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        getAndCheckUser()

        binding.apply {
            fragmentMatchesListRvMatchesList.layoutManager = LinearLayoutManager(context)
        }

        with(viewModel) {
            fetchData()
            matchesLiveData.observe(viewLifecycleOwner) { matches ->
                updateLayout(matches)
            }
        }

        return binding.root
    }

    private fun getAndCheckUser() =
        with(viewModel) {
            setUsernameAndNumbers(
                getStringFromSharedPreferences("Username"),
                getStringFromSharedPreferences("Numbers")
            )
            if (noUserSet()) navigateToUsernameFragment()
        }

    private fun getStringFromSharedPreferences(key: String, defValue: String = "") =
        sharedPreferences.getString(key, defValue).toString()

    private fun navigateToUsernameFragment() =
        findNavController()
            .navigate(MatchesListFragmentDirections.actionMatchesListFragmentToUsernameFragment())

    private fun updateLayout(matches: List<Match>?) {
        hideProgressIndicator()
        if(matches != null) updateMatchesListAdapter(matches)
        else showSomethingWentWrongSnackbar()
    }

    private fun hideProgressIndicator() {
        binding.fragmentMatchesListProgressIndicator.visibility = View.GONE
    }

    private fun updateMatchesListAdapter(matches: List<Match>) {
        binding.fragmentMatchesListRvMatchesList.adapter = createMatchesListAdapter(matches)
    }

    private fun showSomethingWentWrongSnackbar() =
        buildSomethingWentWrongSnackbarWithTryAgainAction().show()

    private fun buildSomethingWentWrongSnackbarWithTryAgainAction() =
        buildSomethingWentWrongSnackbar().setAction(requireContext().getText(R.string.try_again)) {
            fetchData()
        }

    private fun buildSomethingWentWrongSnackbar() =
        Snackbar.make(
            binding.fragmentMatchesListStatsCoordinatorLayout,
            requireContext().getText(R.string.something_went_wrong),
            Snackbar.LENGTH_INDEFINITE
        )

    private fun fetchData() {
        viewModel.fetchData()
        binding.fragmentMatchesListProgressIndicator.visibility = View.VISIBLE
    }

    private fun createMatchesListAdapter(matches: List<Match>) =
        MatchesListAdapter(requireContext(), matches.toTypedArray()) { gameID ->
            navigateToMatchesViewPagerFragment(gameID)
        }

    private fun navigateToMatchesViewPagerFragment(it: Int) =
        findNavController().navigate(
            MatchesListFragmentDirections.actionMatchesListFragmentToMatchDetailsFragment(it)
        )

}