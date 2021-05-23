package com.kaankaplan.recapproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaankaplan.recapproject.R
import com.kaankaplan.recapproject.models.CarsDto
import kotlinx.android.synthetic.main.item_cars.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.CarsViewHolder>() {

    class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val differCallback = object : DiffUtil.ItemCallback<CarsDto>(){
        override fun areItemsTheSame(oldItem: CarsDto, newItem: CarsDto): Boolean {
            return oldItem.carId == newItem.carId
        }

        override fun areContentsTheSame(oldItem: CarsDto, newItem: CarsDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        return CarsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cars, parent, false))
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val carDto = differ.currentList[position]
        holder.itemView.apply {
            carNameText.text = carDto.carName
            brandNameText.text = carDto.brandName
            priceText.text = carDto.dailyPrice.toString() + "₺"
            setOnClickListener {
                onItemClickListener?.let { it(carDto) }
            }
        }
    }

    private var onItemClickListener : ((CarsDto) -> Unit)? = null

    fun setOnItemClickListener(listener : ((CarsDto) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}