package com.mirvan.devtest.employee_feature.domain.repository

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.DeleteEmployee
import kotlinx.coroutines.flow.Flow

interface DeleteEmployeeRepository {
    fun deleteEmployee(employeeId: String): Flow<Resource<DeleteEmployee>>
}
