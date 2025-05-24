package com.example.callaguy.data.remote

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.LoginResponseDto
import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.data.dto.serviceRequest.CreateServiceRequest
import com.example.callaguy.data.dto.serviceRequest.ServiceRequestResponse
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("/customer_auth/register")
    suspend fun register( @Body data : AuthenticationRequestDto)

    @POST("/customer_auth/login")
    suspend fun logIn(@Body data : LoginRequestDto) : LoginResponseDto

    @GET("/services")
    suspend fun getServices(): List<ServiceResponseDto>

    @Multipart
    @POST("/customer_profile/picture")
    suspend fun updateProfilePicture(
        @Part image : MultipartBody.Part
    )

    @GET("/customer_profile/profile_info")
    suspend fun getProfileInfo() : ProfileInfoResponseDto

    @GET("/services/{id}")
    suspend fun getSubServicesByServiceId(
        @Path("id") id : Int
    ) : List<SubServiceResponseDto>

    @POST("/customer_service_request/create")
    suspend fun createServiceRequest( @Body data : CreateServiceRequest ) : ServiceRequestResponse
}