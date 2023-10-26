package com.learning.baseprojectforkan.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.learning.baseprojectforkan.R
import com.learning.baseprojectforkan.data.remote.model.UserRes
import com.learning.baseprojectforkan.databinding.ItemLayoutBinding

class MainAdapter(
    private val users: ArrayList<UserRes.UserResItem>,
    private val onItemClick: (UserRes.UserResItem) -> Unit
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(private var binding: ItemLayoutBinding, onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        //bind data with the component
        @SuppressLint("SetTextI18n")
        fun bind(user: UserRes.UserResItem) {
            binding.apply {
                name.text = user.name
                actorName.text = "Actor: ${user.actor}"
                houseName.text = "House: ${user.house}"
                imageViewAvatar.load(user.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    transformations(CircleCropTransformation())
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(users[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun submitData(list: List<UserRes.UserResItem>) {
        users.addAll(list)
    }

}