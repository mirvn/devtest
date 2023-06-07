package com.mirvan.devtest.employee_feature.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import com.mirvan.devtest.employee_feature.domain.repository.AddEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.DeleteEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.GetAllEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.UpdateEmployeeRepository
import com.mirvan.devtest.employee_feature.presentation.state.AddEmployeeState
import com.mirvan.devtest.employee_feature.presentation.state.DeleteEmployeeState
import com.mirvan.devtest.employee_feature.presentation.state.EmployeesState
import com.mirvan.devtest.employee_feature.presentation.state.UpdateEmployeeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: GetAllEmployeeRepository,
    private val updateEmployeeRepository: UpdateEmployeeRepository,
    private val deleteEmployeeRepository: DeleteEmployeeRepository,
    private val addEmployeeRepository: AddEmployeeRepository
) : ViewModel() {
    var employeeJob: Job? = null

    private val _employeesState = mutableStateOf(EmployeesState())
    val employeeState: State<EmployeesState> = _employeesState

    private val _updateEmployeeState = mutableStateOf(UpdateEmployeeState())
    val updateEmployeeState: State<UpdateEmployeeState> = _updateEmployeeState

    private val _addEmployeeState = mutableStateOf(AddEmployeeState())
    val addEmployeeState: State<AddEmployeeState> = _addEmployeeState

    private val _deleteEmployeeState = mutableStateOf(DeleteEmployeeState())
    val deleteEmployeeState: State<DeleteEmployeeState> = _deleteEmployeeState

    init {
        getAllEmployees()
    }

    fun getAllEmployees() {
        _employeesState.value = employeeState.value.copy(
            employeeState = null,
            isLoading = true
        )
        employeeJob?.cancel()
        employeeJob = viewModelScope.launch {
            employeeRepository.getAllEmployee().onEach { result ->
                val resultData = result.data?.toEmployeesState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _employeesState.value = employeeState.value.copy(
                            employeeState = resultData?.employeeState,
                            isLoading = false,
                            message = resultData?.message
                        )
                    }
                    is Resource.Error -> {
                        _employeesState.value = employeeState.value.copy(
                            employeeState = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _employeesState.value = employeeState.value.copy(
                            employeeState = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateEmployee(employeeId: String, body: UpdateEmployee.Data) {
        _updateEmployeeState.value = updateEmployeeState.value.copy(
            updateEmployeeState = null,
            isLoading = true
        )
        employeeJob?.cancel()
        employeeJob = viewModelScope.launch {
            updateEmployeeRepository.updateEmployee(employeeId, body).onEach { result ->
                val resultData = result.data?.toUpdateEmployeState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _updateEmployeeState.value = updateEmployeeState.value.copy(
                            updateEmployeeState = resultData?.updateEmployeeState,
                            isLoading = false,
                            message = resultData?.updateEmployeeState?.message
                        )
                    }
                    is Resource.Error -> {
                        _updateEmployeeState.value = updateEmployeeState.value.copy(
                            updateEmployeeState = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _updateEmployeeState.value = updateEmployeeState.value.copy(
                            updateEmployeeState = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun addEmployee(body: UpdateEmployee.Data) {
        _addEmployeeState.value = addEmployeeState.value.copy(
            addEmployeeState = null,
            isLoading = true
        )
        employeeJob?.cancel()
        employeeJob = viewModelScope.launch {
            addEmployeeRepository.addEmployee(body).onEach { result ->
                val resultData = result.data?.toAddEmployeeState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _addEmployeeState.value = addEmployeeState.value.copy(
                            addEmployeeState = resultData?.addEmployeeState,
                            isLoading = false,
                            message = resultData?.addEmployeeState?.message
                        )
                    }
                    is Resource.Error -> {
                        _addEmployeeState.value = addEmployeeState.value.copy(
                            addEmployeeState = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _addEmployeeState.value = addEmployeeState.value.copy(
                            addEmployeeState = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun deleteEmployee(employeeId: String) {
        _deleteEmployeeState.value = deleteEmployeeState.value.copy(
            deleteEmployeeState = null,
            isLoading = true
        )
        employeeJob?.cancel()
        employeeJob = viewModelScope.launch {
            deleteEmployeeRepository.deleteEmployee(employeeId).onEach { result ->
                val resultData = result.data?.toDeleteEmployeState()
                val resultMessage = result.message
                when (result) {
                    is Resource.Success -> {
                        _deleteEmployeeState.value = deleteEmployeeState.value.copy(
                            deleteEmployeeState = resultData?.deleteEmployeeState,
                            isLoading = false,
                            message = resultData?.deleteEmployeeState?.message
                        )
                    }
                    is Resource.Error -> {
                        _deleteEmployeeState.value = deleteEmployeeState.value.copy(
                            deleteEmployeeState = null,
                            isLoading = false,
                            message = resultMessage
                        )
                    }
                    is Resource.Loading -> {
                        _deleteEmployeeState.value = deleteEmployeeState.value.copy(
                            deleteEmployeeState = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}
