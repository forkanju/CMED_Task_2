package com.learning.baseprojectforkan.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.learning.baseprojectforkan.R
import com.learning.baseprojectforkan.common.extensions.toastyError
import com.learning.baseprojectforkan.common.utils.Status
import com.learning.baseprojectforkan.data.remote.model.UserRes
import com.learning.baseprojectforkan.databinding.FragmentFirstBinding
import com.learning.baseprojectforkan.ui.main.adapter.MainAdapter
import com.learning.baseprojectforkan.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var adapter: MainAdapter
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }


    private fun setupUI() {
        binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
        //Adapter initialization with item click lambda
        adapter = MainAdapter(arrayListOf()) { user ->
            val gson = Gson()
            val userJson = gson.toJson(user) // Convert the user object to a JSON string
            val bundle = Bundle()
            bundle.putString("user", userJson)
            findNavController().navigate(R.id.action_first_to_second, bundle)
        }
        binding.rvUser.addItemDecoration(
            DividerItemDecoration(
                binding.rvUser.context,
                (binding.rvUser.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvUser.adapter = adapter


    }

    private fun setupObserver() {
        mainViewModel.users.observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    // progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.rvUser.visibility = View.VISIBLE

                }

                Status.LOADING -> {
                    // progressBar.visibility = View.VISIBLE
                    binding.rvUser.visibility = View.GONE
                }

                Status.ERROR -> {
                    //Handle Error
                    // progressBar.visibility = View.GONE
                    toastyError(it.message)
                }
            }
        })
    }

    private fun renderList(users: List<UserRes.UserResItem>) {
        adapter.submitData(users)
        adapter.notifyDataSetChanged()
    }

}

/**Safe Arguments -> to pass arguments in different fragments - see MindOrks docs*/

/**Safe Args is strongly recommended for navigating and passing data, because it ensures type-safety.
 * In some cases, for example if you are not using Gradle, you can't use the Safe Args plugin.
 * In these cases, you can use Bundles to directly pass data.*/