package com.mirvan.devtest.employee_feature.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.domain.repository.GetAllEmployeeRepository
import com.mirvan.devtest.employee_feature.presentation.state.EmployeesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: GetAllEmployeeRepository
) : ViewModel() {
    var employeeJob: Job? = null

    private val _employeesState = mutableStateOf(EmployeesState())
    val employeeState: State<EmployeesState> = _employeesState

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
}
