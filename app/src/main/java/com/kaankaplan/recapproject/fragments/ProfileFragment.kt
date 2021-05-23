package com.kaankaplan.recapproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kaankaplan.recapproject.CarsActivity
import com.kaankaplan.recapproject.viewModels.CarsViewModel
import com.kaankaplan.recapproject.R
import com.kaankaplan.recapproject.models.User
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var viewModel : CarsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        viewModel.customer.observe(viewLifecycleOwner, Observer {
            it?.let {
                val customer = it.data
                firstName.setText(customer.firstName)
                lastName.setText(customer.lastName)
                email.setText(customer.email)
                findeksText.text = "Findeks Puanınız: ${customer.findeks}"
            }
        })

        profileButton.setOnClickListener {
            val userId = viewModel.user.value!!.data.id
            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()
            val email = email.text.toString()
            val password = newPassword.text.toString()

            val user = User(userId, firstName, lastName, email , password)
            viewModel.updateUser(user)
        }
    }
}