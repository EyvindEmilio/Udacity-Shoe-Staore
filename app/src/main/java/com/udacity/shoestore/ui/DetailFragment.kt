package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.MainActivity
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentDetailBinding
import com.udacity.shoestore.models.Shoe

class DetailFragment : Fragment() {


    private lateinit var bind: FragmentDetailBinding
    private var newShoe = Shoe("", 0.0, "", "", listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        bind.lifecycleOwner = this
        bind.shoe = newShoe

        bind.btnCancel.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToListingFragment3()
            )
        }
        bind.btnSave.setOnClickListener { saveShoe() }

        bind.invalidateAll()
        return bind.root
    }

    private fun saveShoe() {
        val sizeString = bind.etSize.text?.toString() ?: ""
        if (newShoe.name.isBlank()) {
            Toast.makeText(requireContext(), R.string.name_is_required, Toast.LENGTH_SHORT).show()
            return
        }

        if (sizeString.isBlank()) {
            Toast.makeText(requireContext(), R.string.size_is_required, Toast.LENGTH_SHORT).show()
            return
        }

        if (newShoe.company.isBlank()) {
            Toast.makeText(requireContext(), R.string.company_is_required, Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            newShoe.size = try {
                sizeString.toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }

        if (newShoe.description.isBlank()) {
            Toast.makeText(requireContext(), R.string.description_is_required, Toast.LENGTH_SHORT)
                .show()
            return
        }

        (requireActivity() as MainActivity).shoeVM.addShoe(newShoe)
        Toast.makeText(requireContext(), R.string.shoe_added_successfully, Toast.LENGTH_SHORT)
            .show()

        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentToListingFragment3()
        )
    }

}