package com.ramprasad.rcountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ramprasad.rcountries.databinding.CountriesListItemBinding
import com.ramprasad.rcountries.model.Countries

/**
 * Created by Ramprasad on 7/31/22.
 */
class CountriesAdapter(private val countriesListData: MutableList<Countries> = mutableListOf()) :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {




    fun setNewCountries(newDataSet: List<Countries>) {
        countriesListData.clear()
        countriesListData.addAll(newDataSet)
        notifyDataSetChanged()
    }

    class CountriesViewHolder(
        private val countriesListItemBinding: CountriesListItemBinding
    ) : RecyclerView.ViewHolder(countriesListItemBinding.root) {
        fun bind(countries: Countries) {
            countriesListItemBinding.countryCode.text = countries.code
            countriesListItemBinding.countryName.text = String.format(countries.name + ",")
            countriesListItemBinding.region.text = countries.region
            countriesListItemBinding.countryCapital.text = countries.capital
            //Toast.makeText(this@CountriesViewHolder, countries.code.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder =
        CountriesViewHolder(
            CountriesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(countriesViewHolder: CountriesViewHolder, position: Int) {
        countriesViewHolder.bind(countriesListData[position])
    }

    override fun getItemCount(): Int = countriesListData.size


}

