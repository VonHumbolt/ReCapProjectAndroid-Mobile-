package com.kaankaplan.recapproject.repository

import com.kaankaplan.recapproject.api.RetrofitInstance
import com.kaankaplan.recapproject.models.*
import retrofit2.Response

class CarsRepository {

    suspend fun getCarsDto() : Response<ListResponseModel<CarsDto>> {
        return RetrofitInstance.api.getCarsDto()
    }

    suspend fun getAllBrands() : Response<ListResponseModel<Brand>> {
        return RetrofitInstance.api.getAllBrands()
    }

    suspend fun getAllColors() : Response<ListResponseModel<Color>> {
        return RetrofitInstance.api.getAllColors()
    }

    suspend fun getCarsDtoByBrandId(brandId : Int) : Response<ListResponseModel<CarsDto>> {
        return RetrofitInstance.api.getCarsDtoByBrandId(brandId)
    }

    suspend fun getCarsDtoByColorId(colorId: Int) : Response<ListResponseModel<CarsDto>> {
        return RetrofitInstance.api.getCarsDtoByColorId(colorId)
    }

    suspend fun getRentalByCarId(carId : Int) : Response<ListResponseModel<Rental>> {
        return RetrofitInstance.api.getRentalByCarId(carId)
    }

    suspend fun getUserCardNumber(userId : Int) : Response<SingleResponseModel<UserCardDetail>>{
        return RetrofitInstance.api.getUserCardNumber(userId)
    }

    suspend fun addUserCardNumber(userCardDetail: UserCardDetail) : Response<ResponseModel> {
        return RetrofitInstance.api.addUserCardNumber(userCardDetail)
    }

    suspend fun pay(userCardDetail: UserCardDetail) : Response<ResponseModel> {
        return RetrofitInstance.api.pay(userCardDetail)
    }

    suspend fun login(userForLogin: UserForLogin) : Response<SingleResponseModel<TokenModel>> {
        return RetrofitInstance.api.login(userForLogin)
    }

    suspend fun register(userForRegister: UserForRegister) : Response<SingleResponseModel<TokenModel>> {
        return RetrofitInstance.api.register(userForRegister)
    }

    suspend fun updateUser(user : User) : Response<SingleResponseModel<User>> {
        return RetrofitInstance.api.updateUser(user)
    }

    suspend fun getUserByEmail(email : String) : Response<SingleResponseModel<User>> {
        return RetrofitInstance.api.getUserByEmail(email)
    }

    suspend fun getCustomerByEmail(email : String) : Response<SingleResponseModel<Customer>> {
        return RetrofitInstance.api.getRegisteredCustomerByEmail(email)
    }

}