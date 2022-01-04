package gst.trainingcourse_ex11_thaonx4.mockanimation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gst.trainingcourse_ex11_thaonx4.mockanimation.R
import gst.trainingcourse_ex11_thaonx4.mockanimation.model.Item
import gst.trainingcourse_ex11_thaonx4.mockanimation.databinding.ItemLayoutBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var binding: ItemLayoutBinding? = null

    private val differUtil = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return newItem == oldItem
        }
    }

    var differ = AsyncListDiffer(this, differUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

        when (position) {
            0 -> setBackgroundColor("#203BC8", "#142CB3")
            1 -> setBackgroundColor("#09C6B5", "#05A596")
            2 -> setBackgroundColor("#FBB245", "#E1A852")
            3 -> setBackgroundColor("#F15D52", "#CC3A2F")
        }
    }

    private fun setBackgroundColor(itemColor: String, iconColor: String) {
        binding?.let {
            it.image.setBackgroundColor(Color.parseColor(iconColor))
            it.ln.setBackgroundColor(Color.parseColor(itemColor))
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            binding.apply {
                image.setImageResource(item.image)
                name.text = item.name
                price.text = "$ " + item.price
            }
        }
    }
}