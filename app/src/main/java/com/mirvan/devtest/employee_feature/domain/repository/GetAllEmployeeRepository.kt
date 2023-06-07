package com.mirvan.devtest.employee_feature.domain.repository

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.Employees
import kotlinx.coroutines.flow.Flow

interface GetAllEmployeeRepository {
    fun getAllEmployee(): Flow<Resource<Employees>>
}
