package com.mirvan.devtest.employee_feature.domain.repository

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.AddEmployee
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import kotlinx.coroutines.flow.Flow

interface AddEmployeeRepository {
    fun addEmployee(body: UpdateEmployee.Data): Flow<Resource<AddEmployee>>
}
