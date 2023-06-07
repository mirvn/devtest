package com.mirvan.devtest.employee_feature.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import com.mirvan.devtest.employee_feature.domain.repository.GetAllEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.UpdateEmployeeRepository
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
    private val updateEmployeeRepository: UpdateEmployeeRepository
) : ViewModel() {
    var employeeJob: Job? = null

    private val _employeesState = mutableStateOf(EmployeesState())
    val employeeState: State<EmployeesState> = _employeesState

    private val _updateEmployeeState = mutableStateOf(UpdateEmployeeState())
    val updateEmployeeState: State<UpdateEmployeeState> = _updateEmployeeState

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
                            employeeState = resultData?.employeeState,
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
}
