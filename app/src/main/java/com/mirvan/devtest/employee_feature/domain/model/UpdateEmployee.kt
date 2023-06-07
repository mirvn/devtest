package com.mirvan.devtest.employee_feature.domain.model

import com.mirvan.devtest.employee_feature.presentation.state.UpdateEmployeeState

data class UpdateEmployee(
    val `data`: Data?,
    val message: String?,
    val status: String?
) {
    data class Data(
        val name: String,
        val salary: String,
        val age: Int
    )

    fun toUpdateEmployeState(): UpdateEmployeeState {
        return UpdateEmployeeState(
            updateEmployeeState = UpdateEmployee(
                data,
                message,
                status
            )
        )
    }
}
