package com.mirvan.devtest.employee_feature.domain.repository

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import kotlinx.coroutines.flow.Flow

interface UpdateEmployeeRepository {
    fun updateEmployee(
        employeeId: String,
        body: UpdateEmployee.Data
    ): Flow<Resource<UpdateEmployee>>
}
