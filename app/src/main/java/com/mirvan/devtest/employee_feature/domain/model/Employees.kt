package com.mirvan.devtest.employee_feature.domain.model

import com.mirvan.devtest.employee_feature.presentation.state.EmployeesState

data class Employees(
    val `data`: List<DataEmployee>?,
    val message: String?,
    val status: String?
) {
    data class DataEmployee(
        val employee_age: Int?,
        val employee_name: String?,
        val employee_salary: Int?,
        val id: Int?,
        val profile_image: String?
    )

    fun toEmployeesState(): EmployeesState {
        return EmployeesState(
            employeeState = Employees(
                data = data,
                message = message,
                status = status
            )
        )
    }
}
