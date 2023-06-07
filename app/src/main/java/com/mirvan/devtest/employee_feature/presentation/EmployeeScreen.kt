package com.mirvan.devtest.employee_feature.presentation // ktlint-disable package-name

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mirvan.devtest.R
import com.mirvan.devtest.employee_feature.domain.model.Employees
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import com.mirvan.devtest.employee_feature.presentation.item.ItemEmployee
import com.mirvan.devtest.employee_feature.presentation.item.ShimmerListItemEmployee
import com.mirvan.devtest.ui.theme.Black
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

var addBarangState = mutableStateOf(false)
var editEmployeeState = mutableStateOf(false)
var loadingState = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    modifier: Modifier = Modifier,
    employeeViewModel: EmployeeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var searchQuery by remember {
        mutableStateOf("")
    }
    var itemSize by remember {
        mutableStateOf(10)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    var isRetryButtonEnabled by remember {
        mutableStateOf(false)
    }
    val employeeState = employeeViewModel.employeeState.value
    val employees = employeeState.employeeState?.data?.filter { employee ->
        employee.employee_name.toString().lowercase(Locale.getDefault()).contains(searchQuery, false)
    }
    isLoading = employeeState.isLoading
    if (!isLoading) itemSize = employees?.size ?: 10
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = employeeState.employeeState?.data != null) {
        if (employeeState.message?.isNotEmpty() == true) Toast.makeText(context, employeeState.message, Toast.LENGTH_SHORT)
            .show()
        if (employeeState.employeeState?.data == null) isRetryButtonEnabled = true
    }
    val updateEmployeeState = employeeViewModel.updateEmployeeState.value

    var detailEmployee by remember {
        mutableStateOf(
            Employees.DataEmployee(
                employee_age = null,
                employee_name = null,
                employee_salary = null,
                id = null,
                profile_image = null
            )
        )
    }

    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            it != SheetValue.PartiallyExpanded
        }
    )

    if (editEmployeeState.value) {
        BottomSheetDialog(
            bottomSheetShowState = editEmployeeState,
            sheetState = modalSheetState
        ) {
            EditEmployeeSheet(
                employeeData = detailEmployee,
                employeeViewModel = employeeViewModel,
                sheetState = modalSheetState
            )
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.employee_list)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addBarangState.value = true
                },
                containerColor = Black,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.add_employee)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { value ->
                    searchQuery = value
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(id = R.string.search_employee))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchQuery = ""
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = "Search")
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = Black.copy(0.8f),
                    unfocusedTextColor = Black.copy(0.8f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp)
            )
            LazyColumn(
                state = rememberLazyListState(),
                userScrollEnabled = true,
                modifier = modifier.padding(top = 16.dp)
            ) {
                item {
                    AnimatedVisibility(visible = isRetryButtonEnabled) {
                        Button(
                            onClick = {
                                isRetryButtonEnabled = false
                                scope.launch(Dispatchers.IO) {
                                    employeeViewModel.getAllEmployees()
                                }
                            },
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
                }
                items(itemSize) { index ->
                    val employee = employees?.get(index)
                    ShimmerListItemEmployee(
                        isLoading = isLoading,
                        contentAfterLoading = {
                            if (employee != null) {
                                ItemEmployee(
                                    employee = employee,
                                    onCLickDetail = {
                                        detailEmployee = employee
                                        editEmployeeState.value = true
                                    }
                                )
                            }
                        },
                        modifier = modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
    AnimatedVisibility(visible = updateEmployeeState.isLoading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.small
                    )
                    .wrapContentSize()
                    .padding(4.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEmployeeSheet(
    modifier: Modifier = Modifier,
//    barangViewModel: BarangViewModel,
    sheetState: SheetState
) {
    val context = LocalContext.current

    var id by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var salary by remember {
        mutableStateOf("0")
    }
    var age by remember {
        mutableStateOf("0")
    }
    var profileimage by remember {
        mutableStateOf("")
    }
    var isDeleteLoading by remember {
        mutableStateOf(false)
    }

//    val storeBarangState = barangViewModel.storeBarangState
//    if (loadingState.value) {
//        LaunchedEffect(key1 = true) {
//            sheetState.hide()
//            addBarangState.value = false
//        }
//    }

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
            .wrapContentSize(),
        userScrollEnabled = true,
        state = rememberLazyListState()
    ) {
        item {
            Text(
                text = stringResource(id = R.string.add_employee),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        stringResource(
                            id = R.string.placeholder_name
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
                placeholder = {
                    Text(
                        stringResource(
                            id = R.string.placeholder_name
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            )
            OutlinedTextField(
                value = salary,
                onValueChange = { salary = it },
                label = {
                    Text(
                        stringResource(
                            id = R.string.placeholder_salary
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
                placeholder = {
                    Text(
                        stringResource(
                            id = R.string.placeholder_salary
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            )
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = {
                    Text(
                        stringResource(
                            id = R.string.employee_age
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
                placeholder = {
                    Text(
                        stringResource(
                            id = R.string.employee_age
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEmployeeSheet(
    modifier: Modifier = Modifier,
    employeeViewModel: EmployeeViewModel,
    sheetState: SheetState,
    employeeData: Employees.DataEmployee
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    var id by remember {
        mutableStateOf(employeeData.id.toString())
    }
    var name by remember {
        mutableStateOf(employeeData.employee_name.toString())
    }
    var salary by remember {
        mutableStateOf(employeeData.employee_salary.toString())
    }
    var age by remember {
        mutableStateOf(employeeData.employee_age.toString())
    }
    var profileimage by remember {
        mutableStateOf(employeeData.profile_image.toString())
    }

    var isUpdateButtonEnabled by remember {
        mutableStateOf(false)
    }

    val updateEmployeeState = employeeViewModel.updateEmployeeState.value
    LaunchedEffect(key1 = updateEmployeeState.isLoading) {
        if (updateEmployeeState.isLoading) {
            sheetState.hide()
            editEmployeeState.value = false
        }
    }
    val ageInt = (
        if (age == "") {
            age = "0"
            age.toInt()
        } else {
            age.toInt()
        }
        )
    val updateEmployeeData = UpdateEmployee.Data(
        name = name,
        salary = salary,
        age = ageInt
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
            .wrapContentSize()
    ) {
        Text(
            text = stringResource(id = R.string.detail_employee),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = {
                Text(
                    stringResource(
                        id = R.string.id_label
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            readOnly = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                isUpdateButtonEnabled = true
            },
            label = {
                Text(
                    stringResource(
                        id = R.string.placeholder_name
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary
            ),
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.placeholder_name
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        )
        OutlinedTextField(
            value = salary,
            onValueChange = {
                salary = it
                isUpdateButtonEnabled = true
            },
            label = {
                Text(
                    stringResource(
                        id = R.string.placeholder_salary
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary
            ),
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.placeholder_salary
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        )
        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                isUpdateButtonEnabled = true
            },
            label = {
                Text(
                    stringResource(
                        id = R.string.employee_age
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary
            ),
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.employee_age
                    ),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        )
        Spacer(modifier = modifier.height(128.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    employeeViewModel.updateEmployee(employeeId = id, body = updateEmployeeData)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        if (!updateEmployeeState.isLoading) {
                            Toast.makeText(
                                context,
                                employeeViewModel.updateEmployeeState.value.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            enabled = isUpdateButtonEnabled
        ) {
            Text(
                text = stringResource(id = R.string.update_employee_data),
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Button(
            onClick = {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        barangViewModel.deleteBarang(
//                            token = token,
//                            id_barang = dataBarang.id.toString()
//                        )
//                        CoroutineScope(Dispatchers.Main).launch {
//                            loadingState.value = deleteBarangState.value.isLoading
//                            delay(2000)
//                            if (!deleteBarangState.value.isLoading) {
//                                loadingState.value = false
//                                Toast.makeText(
//                                    context,
//                                    barangViewModel.deleteBarangState.value.deleteBarangData?.meta?.message.toString(),
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.delete_employee),
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String = ""
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
            titleContentColor = Black
        )
    )
}
