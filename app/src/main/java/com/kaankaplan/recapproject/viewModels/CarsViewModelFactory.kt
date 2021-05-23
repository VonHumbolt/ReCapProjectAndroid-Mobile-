package com.kaankaplan.recapproject.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaankaplan.recapproject.repository.CarsRepository

class CarsViewModelFactory(val app: Application, val repository: CarsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CarsViewModel(app, repository) as T
    }
}