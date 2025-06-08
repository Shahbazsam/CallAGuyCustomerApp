package com.example.callaguy.data.remote

import com.example.callaguy.data.dto.Authentication.AuthenticationRequestDto
import com.example.callaguy.data.dto.Authentication.LoginRequestDto
import com.example.callaguy.data.dto.Authentication.LoginResponseDto
import com.example.callaguy.data.dto.chatScreen.SendSupportMessageDto
import com.example.callaguy.data.dto.chatScreen.SupportMessagesDto
import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.data.dto.serviceRequest.CreateServiceRequest
import com.example.callaguy.data.dto.serviceRequest.GetServiceRequestDto
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.data.dto.supportTicket.CreateSupportTicketDto
import com.example.callaguy.data.dto.supportTicket.SupportTicketsDto
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
    suspend fun createServiceRequest( @Body data : CreateServiceRequest )

    @GET("/customer_service_request")
    suspend fun getServiceRequests() : List<GetServiceRequestDto?>

    @POST("/supportTicket/create")
    suspend fun createSupportTicket( @Body data : CreateSupportTicketDto )

    @GET("/supportTicket/customer_tickets")
    suspend fun getSupportTickets() : List<SupportTicketsDto>

    @POST("/support_messages/create")
    suspend fun sendSupportMessage(data : SendSupportMessageDto)

    @GET("/support_messages/{id}")
    suspend fun getSupportMessages(
        @Path("id") id: Int
    ) : List<SupportMessagesDto>
}