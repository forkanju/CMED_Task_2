package com.learning.baseprojectforkan.ui.main.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.baseprojectforkan.R
import androidx.fragment.app.Fragment
import coil.load
import com.google.gson.Gson
import com.learning.baseprojectforkan.data.remote.model.UserRes
import com.learning.baseprojectforkan.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private val userJson: String? by lazy {
        requireArguments().getString("user")
    }

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userJson?.let {
            val gson = Gson()
            val user = gson.fromJson(it, UserRes.UserResItem::class.java)
            user?.let { user ->
                binding.apply {
                    characterImage.load(user.image) {
                        crossfade(true)
                        placeholder(R.drawable.image)
                    }
                    characterName.text = user.name
                    actorName.text = user.actor
                    houseName.text = user.house
                    dateOfBirth.text = user.dateOfBirth
                }
            }
        } ?: run {
            // Handle the case where the userJson is null or not properly formatted
        }
    }
}

/**Safe Arguments -> to pass arguments in different fragments - see MindOrks docs*/