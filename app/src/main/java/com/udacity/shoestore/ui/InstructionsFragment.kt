package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import com.udacity.shoestore.models.User


class InstructionsFragment : Fragment() {

    private lateinit var bind: FragmentInstructionsBinding
    private lateinit var currentUser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)
        bind.lifecycleOwner = this
        currentUser = InstructionsFragmentArgs.fromBundle(arguments!!).user

        bind.btnContinue.setOnClickListener {
            findNavController().navigate(
                InstructionsFragmentDirections.actionInstructionsFragmentToListingFragment()
            )
        }
        return bind.root
    }

}