package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.vm.LoginVM


class LoginFragment : Fragment() {

    private lateinit var loginVM: LoginVM
    private lateinit var bind: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bind = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        loginVM = ViewModelProvider(this)[LoginVM::class.java]
        bind.loginVM = loginVM
        bind.lifecycleOwner = this

        loginVM.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                LoginVM.Status.EMPTY_EMAIL -> {
                    resetError()
                    bind.tilEmail.error = getString(R.string.email_is_required)
                }
                LoginVM.Status.EMPTY_PASSWORD -> {
                    resetError()
                    bind.tilPassword.error = getString(R.string.password_is_required)
                }
                LoginVM.Status.EMPTY_AND_PASSWORD -> {
                    resetError()
                    bind.tilEmail.error = getString(R.string.email_is_required)
                    bind.tilPassword.error = getString(R.string.password_is_required)
                }
                LoginVM.Status.ALREADY_REGISTERED -> {
                    resetError()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.already_registered_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginVM.Status.USER_NOT_REGISTERED -> {
                    resetError()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.not_registered_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginVM.Status.WRONG_EMAIL_OR_PASSWORD -> {
                    resetError()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.wrong_credentials_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                LoginVM.Status.REGISTRATION_SUCCESS -> {
                    resetError()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.successfully_registration),
                        Toast.LENGTH_SHORT
                    ).show()
                    loginVM.currentUser?.let {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(it, true)
                        )
                    }
                    loginVM.resetStatus()
                    loginVM.resetEmailAndPassword()
                }
                LoginVM.Status.AUTH_SUCCESS -> {
                    resetError()
                    loginVM.currentUser?.let {
                        findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(it, false)
                        )
                    }
                    loginVM.resetStatus()
                    loginVM.resetEmailAndPassword()
                }
            }

        })
        return bind.root
    }

    private fun resetError() {
        bind.tilEmail.error = null
        bind.tilPassword.error = null
    }

    private fun resetInput() {
        loginVM.resetEmailAndPassword()
    }
}