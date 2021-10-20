package com.Moringa.globotour.city

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.Moringa.globotour.R
import java.util.*

class CityAdapter(val context: Context, var cityList: ArrayList<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var currentPosition: Int = -1
        private var currentCity: City? = null

        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private val imvDelete = itemView.findViewById<ImageView>(R.id.imv_delete)
        private val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        private val icFavoriteFilledImage = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.ic_favorite_filled, null
        )
        private val icFavoriteBorderedImage = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.ic_favorite_bordered, null
        )


        fun setData(city: City, position: Int) {
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)

            if (city.isFavorite)
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
            else
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)

            this.currentPosition = position
            this.currentCity = city

        }

        fun setListeners() {
            imvDelete.setOnClickListener(this@CityViewHolder)
            imvFavorite.setOnClickListener(this@CityViewHolder)
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.imv_delete -> deleteItem()
                R.id.imv_favorite -> addToFavorite()
            }
        }

        fun deleteItem() {
            cityList.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            notifyItemRangeChanged(currentPosition, cityList.size)

        }

        fun addToFavorite() {
            currentCity?.isFavorite = !(currentCity?.isFavorite!!)
//
            if (currentCity?.isFavorite!!) {
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
                VacationSpots.favoriteCityList.add(currentCity!!)
//
            } else {
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)
                VacationSpots.favoriteCityList.remove(currentCity)
            }
        }

    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {

            Log.i("CityAdapter", "onCreateViewHolder: ViewHolder created")


            val itemView =
                LayoutInflater.from(context).inflate(R.layout.list_item_city, parent, false)
            return CityViewHolder(itemView)

        }


            override fun getItemCount(): Int {
                return cityList.size
            }

            override fun onBindViewHolder(cityViewholder: CityViewHolder, position: Int) {

                Log.i("CityAdapter", "onBindViewHolder: position: $position")

                val city = cityList[position]
                cityViewholder.setData(city, position)
                cityViewholder.setListeners()

            }
        }


