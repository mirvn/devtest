package com.mirvan.devtest.employee_feature.data.remote.dto

import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee

data class UpdateEmployeeDto(
    val `data`: DataDto,
    val message: String,
    val status: String
) {
    data class DataDto(
        val age: Int,
        val name: String,
        val salary: String
    )

    fun toUpdateEmployee(): UpdateEmployee {
        val updateEmployeeData = UpdateEmployee.Data(
            age = data.age,
            name = data.name,
            salary = data.salary
        )
        return UpdateEmployee(
            data = updateEmployeeData,
            message = message,
            status = status
        )
    }
}
