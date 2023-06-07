package com.mirvan.devtest.employee_feature.domain.model

import com.mirvan.devtest.employee_feature.presentation.state.AddEmployeeState

data class AddEmployee(
    val `data`: Data?,
    val message: String?,
    val status: String?
) {
    data class Data(
        val age: Int,
        val id: Int,
        val name: String,
        val salary: String
    )

    fun toAddEmployeeState(): AddEmployeeState {
        return AddEmployeeState(
            addEmployeeState = AddEmployee(
                data,
                message,
                status
            )
        )
    }
}
