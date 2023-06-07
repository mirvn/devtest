package com.mirvan.devtest.employee_feature.data.remote

import com.mirvan.devtest.employee_feature.data.remote.dto.EmployeesDto
import com.mirvan.devtest.employee_feature.data.remote.dto.UpdateEmployeeDto
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import retrofit2.Response
import retrofit2.http.*

interface EmployeeApi {
    @GET("employees")
    suspend fun getAllEmployees(): Response<EmployeesDto>

//    @DELETE("update/{employeeId}")
    @HTTP(method = "PUT", path = "update/{employeeId}", hasBody = true)
    suspend fun updateEmployeeById(
        @Path(value = "employeeId") employeeId: String,
        @Body body: UpdateEmployee.Data
    ): Response<UpdateEmployeeDto>
}
