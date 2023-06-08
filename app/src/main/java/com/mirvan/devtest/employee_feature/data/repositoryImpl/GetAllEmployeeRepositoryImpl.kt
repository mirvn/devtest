package com.mirvan.devtest.employee_feature.data.repositoryImpl // ktlint-disable package-name

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.data.remote.EmployeeApi
import com.mirvan.devtest.employee_feature.domain.model.Employees
import com.mirvan.devtest.employee_feature.domain.repository.GetAllEmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class GetAllEmployeeRepositoryImpl(
    private val api: EmployeeApi
) : GetAllEmployeeRepository {
    override fun getAllEmployee(): Flow<Resource<Employees>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteLoginData = api.getAllEmployees()
            if (remoteLoginData.isSuccessful) {
                val remoteToDto = remoteLoginData.body()
                val employee = remoteToDto?.toEmployees()
                emit(
                    Resource.Success(
                        Employees(
                            `data` = employee?.data,
                            message = employee?.message,
                            status = employee?.status
                        )
                    )
                )
            } else {
                val contentType = remoteLoginData.headers().get("Content-Type")
                val errorBody = remoteLoginData.errorBody()?.string()

                if (contentType?.contains("application/json") == true) {
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.getString("message")
                    emit(Resource.Error(message = errorMessage, data = null))
                } else {
                    emit(Resource.Error(message = "To many request: ${remoteLoginData.raw().code}, please try again later", data = null))
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
