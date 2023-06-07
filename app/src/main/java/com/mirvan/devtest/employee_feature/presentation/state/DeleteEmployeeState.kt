package com.mirvan.devtest.employee_feature.presentation.state

import com.mirvan.devtest.employee_feature.domain.model.DeleteEmployee

data class DeleteEmployeeState(
    val deleteEmployeeState: DeleteEmployee? = DeleteEmployee(
        data = null,
        message = null,
        status = null
    ),
    val isLoading: Boolean = false,
    val message: String? = ""
)
