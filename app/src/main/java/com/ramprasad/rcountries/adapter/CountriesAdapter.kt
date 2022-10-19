package com.ramprasad.rcountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramprasad.rcountries.databinding.CountriesHeaderListItemBinding
import com.ramprasad.rcountries.databinding.CountriesListItemBinding
import com.ramprasad.rcountries.model.Countries

/**
 * Created by Ramprasad on 7/31/22.
 */
class CountriesAdapter(private val countriesListData: MutableList<Countries> = mutableListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (countriesListData[position].header != null) {
            0
        } else {
            1
        }
    }

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
            // Toast.makeText(this@CountriesViewHolder, countries.code.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderViewHolder(
                CountriesHeaderListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> CountriesViewHolder(
                CountriesListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = countriesListData.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == 0) {
            (holder as? HeaderViewHolder)?.bind(countriesListData[position])
        } else {
            (holder as? CountriesViewHolder)?.bind(countriesListData[position])
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        holder.adapterPosition
        super.onViewRecycled(holder)
    }
}

class HeaderViewHolder(private val headerListItemBinding: CountriesHeaderListItemBinding) :
    RecyclerView.ViewHolder(headerListItemBinding.root) {

    fun bind(countries: Countries) {
        headerListItemBinding.countryHeader.text = countries.header
    }
}
