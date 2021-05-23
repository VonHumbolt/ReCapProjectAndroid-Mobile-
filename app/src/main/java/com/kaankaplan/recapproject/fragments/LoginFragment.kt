package com.kaankaplan.recapproject.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kaankaplan.recapproject.CarsActivity
import com.kaankaplan.recapproject.viewModels.CarsViewModel
import com.kaankaplan.recapproject.R
import com.kaankaplan.recapproject.models.UserForLogin
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var viewModel : CarsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        loginButton.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()

            val userForLogin = UserForLogin(email, password)
            viewModel.login(userForLogin)

            viewModel.token.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                    if(it.success) {
                        viewModel.getCustomerByEmail(email)
                        viewModel.getUserByEmail(email)
                        findNavController().navigate(R.id.action_loginFragment_to_carsFragment)
                    }else {
                        Toast.makeText(requireContext(), "Lütfen email ve parolanızı doğru girdiğinizden emin olun", Toast.LENGTH_LONG).show()
                    }
                }
            } )
        }
    }
}