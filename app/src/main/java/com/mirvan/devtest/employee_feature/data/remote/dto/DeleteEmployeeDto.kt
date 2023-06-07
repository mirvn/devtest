package com.mirvan.devtest.employee_feature.data.remote.dto

import com.mirvan.devtest.employee_feature.domain.model.DeleteEmployee

data class DeleteEmployeeDto(
    val `data`: String,
    val message: String,
    val status: String
) {
    fun toDeleteEmployee(): DeleteEmployee {
        return DeleteEmployee(
            data,
            message,
            status
        )
    }
}
