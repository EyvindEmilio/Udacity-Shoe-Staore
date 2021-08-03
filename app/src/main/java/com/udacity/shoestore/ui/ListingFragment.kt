package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.MainActivity
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentListingBinding
import com.udacity.shoestore.databinding.ItemShoeBinding
import com.udacity.shoestore.models.Shoe

class ListingFragment : Fragment() {


    private lateinit var bind: FragmentListingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_listing, container, false)
        bind.lifecycleOwner = this

        (requireActivity() as MainActivity).shoeVM.shoeList.observe(
            viewLifecycleOwner,
            Observer { shoes ->
                clearList()
                shoes.forEach {
                    val shoeView = bindItem(it)
                    bind.itemContent.addView(shoeView)
                }
            })

        bind.fabAdd.setOnClickListener {
            findNavController().navigate(
                ListingFragmentDirections.actionListingFragmentToDetailFragment()
            )
        }
        return bind.root
    }

    private fun clearList() {
        bind.itemContent.removeAllViews()
    }

    private fun bindItem(shoe: Shoe): View {
        val itemBind = ItemShoeBinding.inflate(layoutInflater)
        itemBind.lifecycleOwner = this
        itemBind.shoe = shoe
        itemBind.invalidateAll()
        return itemBind.root
    }

}