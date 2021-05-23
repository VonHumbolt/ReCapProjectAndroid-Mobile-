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
import com.kaankaplan.recapproject.models.UserForRegister
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {
    lateinit var  viewModel: CarsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        registerButton.setOnClickListener {
            val firstName = firstNameText.text.toString()
            val lastName  = lastNameText.text.toString()
            val email = emailTextView.text.toString()
            val password = passwordTextView.text.toString()

            if(firstNameText.text.isNullOrEmpty() && lastNameText.text.isNullOrEmpty() && emailTextView.text.isNullOrEmpty() && passwordTextView.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show()
            }else {
                val userForRegister = UserForRegister(firstName, lastName, email, password)

                viewModel.register(userForRegister)
            }


            viewModel.token.observe(viewLifecycleOwner, Observer {
                it?.let {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                    if (it.success) {
                        viewModel.getUserByEmail(email)
                        findNavController().navigate(R.id.action_registerFragment_to_carsFragment)
                    } else {
                        Toast.makeText(requireContext(), "Lütfen bilgileri eksiksiz ve doğru doldurduğunuzdan emin olun", Toast.LENGTH_LONG).show()
                    }
                }
            })

        }
    }
}