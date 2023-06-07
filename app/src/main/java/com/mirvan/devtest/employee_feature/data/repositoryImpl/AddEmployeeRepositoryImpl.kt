package com.mirvan.devtest.employee_feature.data.repositoryImpl // ktlint-disable package-name

import android.util.Log
import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.data.remote.EmployeeApi
import com.mirvan.devtest.employee_feature.domain.model.AddEmployee
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import com.mirvan.devtest.employee_feature.domain.repository.AddEmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class AddEmployeeRepositoryImpl(
    private val api: EmployeeApi
) : AddEmployeeRepository {
    override fun addEmployee(body: UpdateEmployee.Data): Flow<Resource<AddEmployee>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteLoginData = api.addEmployee(body)
            if (remoteLoginData.isSuccessful) {
                val remoteToDto = remoteLoginData.body()?.toAddEmployee()
                emit(
                    Resource.Success(
                        AddEmployee(
                            `data` = remoteToDto?.data,
                            message = remoteToDto?.message,
                            status = remoteToDto?.status
                        )
                    )
                )
            } else {
                val contentType = remoteLoginData.headers().get("Content-Type")
                val errorBody = remoteLoginData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.getString("message")
                    emit(Resource.Error(message = errorMessage))
                } else {
                    emit(Resource.Error(message = "To many request: ${remoteLoginData.raw().code}, please try again later"))
                }
            }
        } catch (e: HttpException) {
            // in case for invalid response
            emit(
                Resource.Error(
                    message = e.message(),
                    `data` = null
                )
            )
        } catch (e: IOException) {
            // wrong parsing, server error, or no connection case
            emit(
                Resource.Error(
                    message = e.message,
                    `data` = null
                )
            )
        }
    }
}
