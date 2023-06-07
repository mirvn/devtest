package com.mirvan.devtest.employee_feature.domain.model

import com.mirvan.devtest.employee_feature.presentation.state.DeleteEmployeeState

data class DeleteEmployee(
    val `data`: String?,
    val message: String?,
    val status: String?
) {
    fun toDeleteEmployeState(): DeleteEmployeeState {
        return DeleteEmployeeState(
            deleteEmployeeState = DeleteEmployee(
                data,
                message,
                status
            )
        )
    }
}
