package com.junaid.groceryappassignment.view.fragments


import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.junaid.groceryappassignment.R
import com.junaid.groceryappassignment.databinding.FragmentHomeBinding
import com.junaid.groceryappassignment.databinding.FragmentNavMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_nav_main) {


    private lateinit var binding: FragmentNavMainBinding
    private val childNavController by lazy { childFragmentManager.findFragmentById(R.id.nav_host_main)!!.findNavController() }


    override fun setupViews() {
        binding= FragmentNavMainBinding.bind(parentView)
        binding.bottomNav.setupWithNavController(childNavController)
        binding.ivAddGrocery.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionNavMainFragmentToCreateGroceryFragment(null))
        }
    }

}