package com.example.callaguy.domain.usecase

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
            ResultClass.Authorized(response.map { tickets ->
                tickets.toModel()
            })
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
}