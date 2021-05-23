package com.kaankaplan.recapproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kaankaplan.recapproject.repository.CarsRepository
import com.kaankaplan.recapproject.viewModels.CarsViewModelFactory
import com.kaankaplan.recapproject.viewModels.CarsViewModel

class CarsActivity : AppCompatActivity() {

    lateinit var viewModel: CarsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars)

        val repository = CarsRepository()
        val viewModelFactory = CarsViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CarsViewModel::class.java)
    }
}