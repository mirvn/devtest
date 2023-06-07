package com.mirvan.devtest.employee_feature.data.remote

import com.mirvan.devtest.employee_feature.data.remote.dto.EmployeesDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {
    @GET("employees")
    suspend fun getAllEmployees(): Response<EmployeesDto>

    @DELETE("delete/{employeeId}")
    suspend fun deleteEmployeeById(@Path(value = "employeeId") employeeId: String): Response<EmployeesDto>
}
