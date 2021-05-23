package com.kaankaplan.recapproject.api

import com.kaankaplan.recapproject.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CarsAPI {

    @GET("api/cars/getCarDetailDtos")
    suspend fun getCarsDto() : Response<ListResponseModel<CarsDto>>

    @GET("api/brands/getall")
    suspend fun getAllBrands() : Response<ListResponseModel<Brand>>

    @GET("api/colors/getall")
    suspend fun getAllColors(): Response<ListResponseModel<Color>>

    @GET("api/cars/getCarDetailByBrandId/")
    suspend fun getCarsDtoByBrandId(@Query("brandId") brandId : Int) : Response<ListResponseModel<CarsDto>>

    @GET("api/cars/getCarDetailByColorId/")
    suspend fun getCarsDtoByColorId(@Query("colorId") colorId : Int) : Response<ListResponseModel<CarsDto>>

    @GET("api/rentals/getRentalByCarId/")
    suspend fun getRentalByCarId(@Query("carId") carId: Int) : Response<ListResponseModel<Rental>>

    @GET("api/rentals/getUserCardNumber/")
    suspend fun getUserCardNumber(@Query("userId") userId: Int) : Response<SingleResponseModel<UserCardDetail>>

    @POST("api/rentals/updateCardNumber/")
    suspend fun addUserCardNumber(@Body userCardDetail : UserCardDetail) : Response<ResponseModel>

    @POST("api/rentals/pay/")
    suspend fun pay(@Body userCardDetail: UserCardDetail) : Response<ResponseModel>

    @POST("api/auth/login/")
    suspend fun login(@Body userForLogin: UserForLogin) : Response<SingleResponseModel<TokenModel>>

    @POST("api/auth/register/")
    suspend fun register(@Body userForRegister : UserForRegister) : Response<SingleResponseModel<TokenModel>>

    @GET("api/auth/getByEmail/")
    suspend fun getUserByEmail(@Query("email") email : String) : Response<SingleResponseModel<User>>

    @POST("api/auth/updateUser/")
    suspend fun updateUser(@Body user: User) : Response<SingleResponseModel<User>>

    @GET("api/customers/getRegisteredCustomerByEmail/")
    suspend fun getRegisteredCustomerByEmail(@Query("email") email : String) : Response<SingleResponseModel<Customer>>
}