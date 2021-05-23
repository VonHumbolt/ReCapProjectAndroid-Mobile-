package com.kaankaplan.recapproject.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankaplan.recapproject.CarsApplication
import com.kaankaplan.recapproject.models.*
import com.kaankaplan.recapproject.repository.CarsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class CarsViewModel(app: Application, val repository: CarsRepository) : AndroidViewModel(app) {

    val cars : MutableLiveData<ListResponseModel<Car>> = MutableLiveData()
    val carsDto : MutableLiveData<ListResponseModel<CarsDto>> = MutableLiveData()
    val brands : MutableLiveData<ListResponseModel<Brand>> = MutableLiveData()
    val colors: MutableLiveData<ListResponseModel<Color>> = MutableLiveData()
    val token : MutableLiveData<SingleResponseModel<TokenModel>> = MutableLiveData()

    val rental : MutableLiveData<ListResponseModel<Rental>> = MutableLiveData()
    val user  :MutableLiveData<SingleResponseModel<User>> = MutableLiveData()
    val customer: MutableLiveData<SingleResponseModel<Customer>> = MutableLiveData()
    val userCardNumber: MutableLiveData<SingleResponseModel<UserCardDetail>> = MutableLiveData()

    val error : MutableLiveData<String> = MutableLiveData()

    init {
        try {
            if (hasInternetConnection()) {
                getCarsDto()
                getAllBrands()
                getAllColors()
            }else {
                error.postValue("Internet Bağlantısı Yok")
            }
        }catch (t : Throwable) {
            when(t) {
                is IOException -> error.postValue("Internet Hatası")
                else -> error.postValue("Conversion Hatası")
            }
        }
    }


    fun getCarsDto() {
        viewModelScope.launch {
            val response = repository.getCarsDto()
            response.body()?.let {
                carsDto.postValue(it)
            }
        }
    }

    fun getAllBrands() {
        viewModelScope.launch {
            val response = repository.getAllBrands()
            response.body()?.let {
                brands.postValue(it)
            }
        }
    }

    fun getAllColors() {
        viewModelScope.launch {
            val response = repository.getAllColors()
            response.body()?.let {
                colors.postValue(it)
            }
        }
    }

    fun getCarsDtoByBrandId(brandId : Int) {
        viewModelScope.launch {
            val response = repository.getCarsDtoByBrandId(brandId)
            response.body()?.let {
                carsDto.postValue(it)
            }
        }
    }

    fun getCarsDtoByColorId(colorId : Int) {
        viewModelScope.launch {
            val response = repository.getCarsDtoByColorId(colorId)
            response.body()?.let {
                carsDto.postValue(it)
            }
        }
    }

    fun getRentalByCarId(carId : Int) {
        viewModelScope.launch {
            val response = repository.getRentalByCarId(carId)
            response.body()?.let {
                rental.postValue(it)
            }
        }
    }

    fun getUserCardNumber(userId : Int) {
        viewModelScope.launch {
            val response = repository.getUserCardNumber(userId)
            response.body()?.let {
                userCardNumber.postValue(it)
            }
        }
    }

    fun addUserCardNumber(userCardDetail: UserCardDetail) = viewModelScope.launch { repository.addUserCardNumber(userCardDetail) }

    fun pay(userCardDetail: UserCardDetail) = viewModelScope.launch { repository.pay(userCardDetail) }

    fun login(userForLogin: UserForLogin) {
        viewModelScope.launch {
            val response = repository.login(userForLogin)
            response.body()?.let{
                token.postValue(it)
            }
        }
    }

    fun register(userForRegister: UserForRegister){
        viewModelScope.launch {
            val response = repository.register(userForRegister)
            response.body()?.let {
                token.postValue(it)
            }
        }
    }

    fun updateUser(userForUpdate: User) {
        viewModelScope.launch {
            val response = repository.updateUser(userForUpdate)
            response.body()?.let {
                user.postValue(it)
            }
        }
    }

    fun getUserByEmail(email : String) {
        viewModelScope.launch {
            val response = repository.getUserByEmail(email)
            response.body()?.let {
                user.postValue(it)
            }
        }
    }

    fun getCustomerByEmail(email : String) {
        viewModelScope.launch {
            val response = repository.getCustomerByEmail(email)
            response.body()?.let {
                customer.postValue(it)
            }
        }
    }


    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<CarsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}