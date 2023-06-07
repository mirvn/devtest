package com.mirvan.devtest.employee_feature.data.remote.dto

import com.mirvan.devtest.employee_feature.domain.model.AddEmployee

data class AddEmployeeDto(
    val `data`: Data,
    val message: String,
    val status: String
) {
    data class Data(
        val age: Int,
        val id: Int,
        val name: String,
        val salary: String
    )

    fun toAddEmployee(): AddEmployee {
        return AddEmployee(
            data = AddEmployee.Data(
                age = data.age,
                id = data.id,
                name = data.name,
                salary = data.salary
            ),
            message,
            status
        )
    }
}
