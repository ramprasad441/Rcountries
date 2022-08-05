package com.ramprasad.rcountries.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramprasad.rcountries.adapter.CountriesAdapter
import com.ramprasad.rcountries.commons.ResponseState
import com.ramprasad.rcountries.databinding.CountriesFragmentBinding
import com.ramprasad.rcountries.viewmodel.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ramprasad on 7/30/22.
 */
@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private val binding by lazy {
        CountriesFragmentBinding.inflate(layoutInflater)
    }

    private val countriesAdapter by lazy {
        CountriesAdapter()
    }

    private val countriesViewModel: CountriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.countryRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = countriesAdapter
        }


        countriesViewModel.countries.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.LOADING -> {
                    binding.countryProgress.visibility = View.VISIBLE
                    binding.countryRV.visibility = View.GONE
                }

                is ResponseState.ERROR -> {
                    binding.countryProgress.visibility = View.GONE
                    binding.countryRV.visibility = View.GONE

                    state.error.localizedMessage?.let {
                        showErrorMessage(it) {
                            countriesViewModel.getListOfAllCountries()
                        }
                    }
                }

                is ResponseState.SUCCESS -> {
                    binding.countryProgress.visibility = View.GONE
                    binding.countryRV.visibility = View.VISIBLE
                    countriesAdapter.setNewCountries(state.countries)
                }

            }
        }


        countriesViewModel.getListOfAllCountries()

        return binding.root
    }

    private fun showErrorMessage(message: String = "Working on the issues", retry: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error Occurred While Fetching the Countries List")
            .setPositiveButton("retry") { dialog, _ ->
                dialog.dismiss()
                retry()
            }
            .setNegativeButton("dismiss") { dialog, _ ->
                dialog.dismiss()
            }
            .setMessage(message)
            .create()
            .show()
    }
}
