package com.mirvan.devtest.employee_feature.presentation.state

import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee

data class UpdateEmployeeState(
    val updateEmployeeState: UpdateEmployee? = UpdateEmployee(
        data = null,
        message = null,
        status = null
    ),
    val isLoading: Boolean = false,
    val message: String? = ""
)