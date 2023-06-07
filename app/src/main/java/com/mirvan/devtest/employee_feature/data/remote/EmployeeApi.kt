package com.mirvan.devtest.employee_feature.data.remote

import com.mirvan.devtest.employee_feature.data.remote.dto.AddEmployeeDto
import com.mirvan.devtest.employee_feature.data.remote.dto.DeleteEmployeeDto
import com.mirvan.devtest.employee_feature.data.remote.dto.EmployeesDto
import com.mirvan.devtest.employee_feature.data.remote.dto.UpdateEmployeeDto
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import retrofit2.Response
import retrofit2.http.*

interface EmployeeApi {
    @GET("employees")
    suspend fun getAllEmployees(): Response<EmployeesDto>

    @HTTP(method = "PUT", path = "update/{employeeId}", hasBody = true)
    suspend fun updateEmployeeById(
        @Path(value = "employeeId") employeeId: String,
        @Body body: UpdateEmployee.Data
    ): Response<UpdateEmployeeDto>

    @HTTP(method = "DELETE", path = "delete/{employeeId}", hasBody = true)
    suspend fun deleteEmployeeById(
        @Path(value = "employeeId") employeeId: String
    ): Response<DeleteEmployeeDto>

    @POST("create")
    suspend fun addEmployee(
        @Body body: UpdateEmployee.Data
    ): Response<AddEmployeeDto>
}
