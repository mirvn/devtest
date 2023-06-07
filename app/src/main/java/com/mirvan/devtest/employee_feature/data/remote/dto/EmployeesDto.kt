package com.mirvan.devtest.employee_feature.data.remote.dto

import com.mirvan.devtest.employee_feature.domain.model.Employees

data class EmployeesDto(
    val `data`: List<DataEmployeeDto>,
    val message: String,
    val status: String
) {
    data class DataEmployeeDto(
        val employee_age: Int,
        val employee_name: String,
        val employee_salary: Int,
        val id: Int,
        val profile_image: String
    )

    fun toEmployees(): Employees {
        val listEmployee = mutableListOf<Employees.DataEmployee>()
        data.map {
            val employee = Employees.DataEmployee(
                employee_age = it.employee_age,
                employee_name = it.employee_name,
                employee_salary = it.employee_salary,
                id = it.id,
                profile_image = it.profile_image
            )
            listEmployee.add(employee)
        }
        return Employees(
            data = listEmployee,
            message = message,
            status = status
        )
    }
}
