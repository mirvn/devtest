package com.mirvan.devtest.employee_feature.presentation.state

import com.mirvan.devtest.employee_feature.domain.model.AddEmployee

data class AddEmployeeState(
    val addEmployeeState: AddEmployee? = AddEmployee(
        data = null,
        message = null,
        status = null
    ),
    val isLoading: Boolean = false,
    val message: String? = ""
)