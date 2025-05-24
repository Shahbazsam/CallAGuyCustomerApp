package com.example.callaguy.domain.validation

import com.example.callaguy.presentation.serviceRequest.ServiceRequestFormState

class ValidateServiceRequestForm {
    fun validate(data : ServiceRequestFormState) : Boolean {
        return data.subServiceId != null &&
                data.preferredDate != null &&
                data.preferredTime != null &&
                data.address.isNotBlank()
    }
}