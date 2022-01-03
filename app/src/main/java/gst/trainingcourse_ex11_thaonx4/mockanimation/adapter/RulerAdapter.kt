package gst.trainingcourse_ex11_thaonx4.mockanimation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gst.trainingcourse_ex11_thaonx4.mockanimation.databinding.ItemRulerBinding

class RulerAdapter : RecyclerView.Adapter<RulerAdapter.ViewHolder>() {

    private var binding: ItemRulerBinding? = null

    private val differUtil = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return newItem.alpha == oldItem.alpha
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return newItem == oldItem
        }
    }

    var differ = AsyncListDiffer(this, differUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RulerAdapter.ViewHolder {
        binding = ItemRulerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RulerAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemRulerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            if (position % 4 == 0) {
                binding.value1.visibility = View.GONE
                binding.value2.visibility = View.VISIBLE
            } else {
                binding.value1.visibility = View.VISIBLE
                binding.value2.visibility = View.GONE
            }
        }
    }
}