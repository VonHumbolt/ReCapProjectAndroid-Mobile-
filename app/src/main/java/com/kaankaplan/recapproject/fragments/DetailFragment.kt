package com.kaankaplan.recapproject.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.kaankaplan.recapproject.CarsActivity
import com.kaankaplan.recapproject.viewModels.CarsViewModel
import com.kaankaplan.recapproject.R
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment(R.layout.fragment_detail) {
    lateinit var viewModel : CarsViewModel

    val args : DetailFragmentArgs by navArgs()

    var currentRentTime : Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as CarsActivity).viewModel

        val carDto = args.carDto

        viewModel.getRentalByCarId(carDto.carId)

        // Glide.with(this).load(carDto.imagePath).into(carImageView) --> Load for image
        carNameTextView.text = "Model: ${carDto.carName}"
        brandNameTextView.text = "Marka: ${carDto.brandName}"
        priceTextView.text = carDto.dailyPrice.toString() + "₺"
        findeksTextView.text = "${carDto.findeks}"
        colorTextView.text = "Renk: ${carDto.colorName}"
        modelYearTextView.text = "Yıl: ${carDto.modelYear}"


        rentDateLayout.setStartIconOnClickListener{
            selectDateFromDatePicker(rentDateTextView)
            println(currentRentTime)
        }

        returnDateLayout.setStartIconOnClickListener {
            selectDateFromDatePicker(returnDateTextView)
        }

        rentButton.setOnClickListener {

           if(!rentDateTextView.text.isNullOrEmpty() && !returnDateTextView.text.isNullOrEmpty()) {
               val dateFormat = SimpleDateFormat("dd.MM.YYYY")
               currentRentTime = dateFormat.parse(rentDateTextView.text.toString()).time
               println(currentRentTime)
           }else {
               Toast.makeText(requireContext(),"Lütfen kiralamak istediğiniz tarihleri seçiniz.",Toast.LENGTH_LONG).show()
           }

            if (!isCarRentableBetweenSelectedDates()) {
                Toast.makeText(requireContext(), "Araç seçtiğiniz tarihler arasında kiralanmış durumda.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (viewModel.customer.value != null) {
                val customerFindeks = viewModel.customer.value!!.data.findeks
                if (customerFindeks < carDto.findeks){
                    Toast.makeText(requireContext(), "Findeks puanınız bu aracı kiralamak için yeterli değil.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            if(!rentDateTextView.text.isNullOrEmpty() && !returnDateTextView.text.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_detailFragment_to_rentCarFragment)
            }else  {
                Toast.makeText(requireContext(), "Lütfen alış ve dönüş tarihlerini seçiniz!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun isCarRentableBetweenSelectedDates() : Boolean{
        val rentDate = viewModel.rental.value!!.data[0].rentDate
        val returnDate = viewModel.rental.value!!.data[0].returnDate

        if( rentDate != null && returnDate != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val rentTime = dateFormat.parse(rentDate).time
            val returnTime = dateFormat.parse(returnDate).time

            if (currentRentTime >= rentTime && currentRentTime <= returnTime){
                return false
            }
        }
        return true
    }


    private fun selectDateFromDatePicker(view: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, y, m, d ->
                view.text = "" + d + "." + m + "." + y

//                val dateFormat = SimpleDateFormat("dd.MM.YYYY")
//                date = dateFormat.parse("" + d + "." + m + "." + y)

            }, year, month, day
        )

        datePicker.show()

    }
}