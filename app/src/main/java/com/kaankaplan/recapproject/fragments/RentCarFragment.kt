package com.kaankaplan.recapproject.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kaankaplan.recapproject.CarsActivity
import com.kaankaplan.recapproject.viewModels.CarsViewModel
import com.kaankaplan.recapproject.R
import com.kaankaplan.recapproject.models.UserCardDetail
import kotlinx.android.synthetic.main.fragment_rent.*


class RentCarFragment : Fragment(R.layout.fragment_rent) {
    lateinit var viewModel : CarsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        payButton.setOnClickListener {
            val firstName = nameText.text.toString()
            val lastName = lastNameText.text.toString()
            val cardNumber = cardNumberText.text.toString()

            if (nameText.text.isNullOrEmpty() && lastNameText.text.isNullOrEmpty() && cardNumberText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Lütfen alanları eksiksiz doldurunuz", Toast.LENGTH_SHORT).show()
            } else {

                if (viewModel.user.value == null) {
                    findNavController().navigate(R.id.action_rentCarFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Lütfen önce giriş yapınız", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val userCardDetail = UserCardDetail(viewModel.user.value!!.data.id,cardNumber)

                MaterialAlertDialogBuilder(requireContext()).setTitle("Kredi Kartı Kaydedilsin Mi?")
                    .setNegativeButton("Hayır"){ _,_ ->

                    }
                    .setPositiveButton("Evet"){ _ , _ ->
                        viewModel.addUserCardNumber(userCardDetail)
                    }.show()

                viewModel.pay(userCardDetail)
                Toast.makeText(requireContext(), "Ödeme İşlemi Tamamlandı", Toast.LENGTH_SHORT).show()
            }

        }
    }
}