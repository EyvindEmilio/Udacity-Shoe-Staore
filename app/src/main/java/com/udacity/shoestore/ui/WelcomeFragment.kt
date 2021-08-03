package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentWelcomeBinding
import timber.log.Timber

class WelcomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var bind: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        bind.lifecycleOwner = this

        val args = WelcomeFragmentArgs.fromBundle(arguments!!)

        bind.currentUser = args.user
        bind.isNewUser = args.isNewUser

        Timber.d("user: ${bind.currentUser}")

        bind.invalidateAll()

        bind.btnContinue.setOnClickListener {
            val user = bind.currentUser!!
            findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToInstructionsFragment(user)
            )
        }
        return bind.root
    }

}