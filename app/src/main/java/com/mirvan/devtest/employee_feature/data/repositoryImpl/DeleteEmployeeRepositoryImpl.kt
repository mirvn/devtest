package com.mirvan.devtest.employee_feature.data.repositoryImpl // ktlint-disable package-name

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.data.remote.EmployeeApi
import com.mirvan.devtest.employee_feature.domain.model.DeleteEmployee
import com.mirvan.devtest.employee_feature.domain.repository.DeleteEmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class DeleteEmployeeRepositoryImpl(
    private val api: EmployeeApi
) : DeleteEmployeeRepository {
    override fun deleteEmployee(employeeId: String): Flow<Resource<DeleteEmployee>> = flow {
        emit(Resource.Loading())

        // make Api Call
        try {
            val remoteLoginData = api.deleteEmployeeById(employeeId)
            if (remoteLoginData.isSuccessful) {
                val remoteToDto = remoteLoginData.body()?.toDeleteEmployee()
                emit(
                    Resource.Success(
                        DeleteEmployee(
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
