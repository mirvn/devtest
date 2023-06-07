package com.mirvan.devtest.employee_feature.presentation.state

import com.mirvan.devtest.employee_feature.domain.model.Employees

data class EmployeesState(
    val employeeState: Employees? = Employees(
        data = null,
        message = null,
        status = null
    ),
    val isLoading: Boolean = false,
    val message: String? = ""
)