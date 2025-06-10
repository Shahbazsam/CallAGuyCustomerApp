package com.example.callaguy.domain.usecase

import android.util.Log
import com.example.callaguy.domain.model.CreateSupportTicketModel
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.SupportTicketsModel
import com.example.callaguy.domain.repository.SupportTicketRepository
import retrofit2.HttpException

class SupportTicketUseCase(
    private val repository: SupportTicketRepository
) {
    suspend fun createTicket(data: CreateSupportTicketModel): ResultClass<Unit> {
        return try {
            repository.createTicket(data = data.toDto())
            ResultClass.Authorized()
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }

    suspend fun getSupportTickets(): ResultClass<List<SupportTicketsModel>> {
        return try {
            val response = repository.getSupportTickets()
            Log.d("response" , "$response")
            ResultClass.Authorized(response.map { tickets ->
                tickets.toModel()
            })
        } catch (e: HttpException) {
            Log.d("tickets1", "${e.message}")
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            Log.d("tickets", "${e.message}")
            ResultClass.UnKnownError()
        }
    }
}