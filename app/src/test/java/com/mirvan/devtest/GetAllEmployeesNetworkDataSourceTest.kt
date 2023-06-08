package com.mirvan.devtest

import com.mirvan.devtest.Core.Utils.Resource
import com.mirvan.devtest.employee_feature.data.remote.EmployeeApi
import com.mirvan.devtest.employee_feature.data.repositoryImpl.AddEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.DeleteEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.GetAllEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.UpdateEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.domain.model.AddEmployee
import com.mirvan.devtest.employee_feature.domain.model.DeleteEmployee
import com.mirvan.devtest.employee_feature.domain.model.Employees
import com.mirvan.devtest.employee_feature.domain.model.UpdateEmployee
import com.mirvan.devtest.utils.enqueueResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetAllEmployeesNetworkDataSourceTest {

    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient.Builder()
        .build()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployeeApi::class.java)

    private val employeeRepository = GetAllEmployeeRepositoryImpl(api)
    private val createEmployeeRepository = AddEmployeeRepositoryImpl(api)
    private val updateEmployeeRepository = UpdateEmployeeRepositoryImpl(api)
    private val deleteEmployeeRepository = DeleteEmployeeRepositoryImpl(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch get all employees correctly given 200 response`() {
        mockWebServer.enqueueResponse("get-all-employees-response.json", 200)

        runBlocking {
            employeeRepository.getAllEmployee().collectLatest { result ->
                val ecpectedData = listOf(
                    Employees.DataEmployee(
                        employee_age = 61,
                        employee_name = "Tiger Nixon",
                        employee_salary = 320800,
                        id = 1,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 63,
                        employee_name = "Garrett Winters",
                        employee_salary = 170750,
                        id = 2,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 66,
                        employee_name = "Ashton Cox",
                        employee_salary = 86000,
                        id = 3,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 22,
                        employee_name = "Cedric Kelly",
                        employee_salary = 433060,
                        id = 4,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 33,
                        employee_name = "Airi Satou",
                        employee_salary = 162700,
                        id = 5,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 61,
                        employee_name = "Brielle Williamson",
                        employee_salary = 372000,
                        id = 6,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 59,
                        employee_name = "Herrod Chandler",
                        employee_salary = 137500,
                        id = 7,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 55,
                        employee_name = "Rhona Davidson",
                        employee_salary = 327900,
                        id = 8,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 39,
                        employee_name = "Colleen Hurst",
                        employee_salary = 205500,
                        id = 9,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 23,
                        employee_name = "Sonya Frost",
                        employee_salary = 103600,
                        id = 10,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 30,
                        employee_name = "Jena Gaines",
                        employee_salary = 90560,
                        id = 11,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 22,
                        employee_name = "Quinn Flynn",
                        employee_salary = 342000,
                        id = 12,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 36,
                        employee_name = "Charde Marshall",
                        employee_salary = 470600,
                        id = 13,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 43,
                        employee_name = "Haley Kennedy",
                        employee_salary = 313500,
                        id = 14,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 19,
                        employee_name = "Tatyana Fitzpatrick",
                        employee_salary = 385750,
                        id = 15,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 66,
                        employee_name = "Michael Silva",
                        employee_salary = 198500,
                        id = 16,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 64,
                        employee_name = "Paul Byrd",
                        employee_salary = 725000,
                        id = 17,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 59,
                        employee_name = "Gloria Little",
                        employee_salary = 237500,
                        id = 18,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 41,
                        employee_name = "Bradley Greer",
                        employee_salary = 132000,
                        id = 19,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 35,
                        employee_name = "Dai Rios",
                        employee_salary = 217500,
                        id = 20,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 30,
                        employee_name = "Jenette Caldwell",
                        employee_salary = 345000,
                        id = 21,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 40,
                        employee_name = "Yuri Berry",
                        employee_salary = 675000,
                        id = 22,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 21,
                        employee_name = "Caesar Vance",
                        employee_salary = 106450,
                        id = 23,
                        profile_image = ""
                    ),
                    Employees.DataEmployee(
                        employee_age = 23,
                        employee_name = "Doris Wilder",
                        employee_salary = 85600,
                        id = 24,
                        profile_image = ""
                    )
                )
                val expected = Employees(
                    data = ecpectedData,
                    message = "Successfully! All records has been fetched.",
                    status = "success"
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: Employees = result.data!!
                        assertEquals(expected.data?.size, actual.data?.size)
                        for (i in 0 until expected.data?.size!!) {
                            val expectedEmployee = expected.data!![i]
                            val actualEmployee = actual.data!![i]

                            assertEquals(expectedEmployee.employee_age, actualEmployee.employee_age)
                            assertEquals(
                                expectedEmployee.employee_name,
                                actualEmployee.employee_name
                            )
                            assertEquals(
                                expectedEmployee.employee_salary,
                                actualEmployee.employee_salary
                            )
                            assertEquals(expectedEmployee.id, actualEmployee.id)
                            assertEquals(
                                expectedEmployee.profile_image,
                                actualEmployee.profile_image
                            )
                        }
                    }
                    else -> {
                        val actual = result.data
                        assertNotEquals(expected, actual)
                    }
                }
            }
        }
    }

    @Test
    fun `should fetch get all employees given 429 response`() {
        mockWebServer.enqueueResponse("429-response.json", 429)

        runBlocking {
            employeeRepository.getAllEmployee().collectLatest { result ->
                val expected = "To many request: 429, please try again later"
                when (result) {
                    is Resource.Error -> {
                        val actual: String? = result.message
                        assertEquals(expected, actual)
                        println("actual-assertEquals: $actual")
                        println("expected-assertEquals: $expected")
                    }
                    else -> {
                        val actual: Employees? = result.data
                        assertNotEquals(expected, actual)
                        println("actual-assertNotEquals: $actual")
                        println("expected-assertNotEquals: $expected")
                    }
                }
            }
        }
    }

    @Test
    fun `should create employee correctly given 200 response`() {
        mockWebServer.enqueueResponse("create-employee-response.json", 200)

        runBlocking {
            createEmployeeRepository.addEmployee(
                body = UpdateEmployee.Data(
                    name = "Timo",
                    salary = "50000000",
                    age = 26
                )
            ).collectLatest { result ->
                val expected = AddEmployee(
                    data = AddEmployee.Data(
                        age = 26,
                        id = 5697,
                        name = "Timo",
                        salary = "50000000"
                    ),
                    message = "Successfully! Record has been added.",
                    status = "success"
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: AddEmployee = result.data!!
                        assertEquals(expected.data, actual.data)
                    }
                    else -> {
                        val actual = result.data
                        assertNotEquals(expected, actual)
                    }
                }
            }
        }
    }

    @Test
    fun `shouldn't create employee correctly given 429 response`() {
        mockWebServer.enqueueResponse("429-response.json", 429)

        runBlocking {
            createEmployeeRepository.addEmployee(
                body = UpdateEmployee.Data(
                    name = "Timo",
                    salary = "50000000",
                    age = 26
                )
            ).collectLatest { result ->
                val expected = "To many request: 429, please try again later"
                when (result) {
                    is Resource.Error -> {
                        val actual: String? = result.message
                        assertEquals(expected, actual)
                        println("actual-assertEquals: $actual")
                        println("expected-assertEquals: $expected")
                    }
                    else -> {
                        val actual: String? = result.message
                        assertNotEquals(expected, actual)
                        println("actual-assertNotEquals: $actual")
                        println("expected-assertNotEquals: $expected")
                    }
                }
            }
        }
    }

    @Test
    fun `should update employee correctly given 200 response`() {
        mockWebServer.enqueueResponse("update-employee-response.json", 200)

        runBlocking {
            updateEmployeeRepository.updateEmployee(
                employeeId = "1",
                body = UpdateEmployee.Data(
                    name = "Timo",
                    salary = "50000000",
                    age = 26
                )
            ).collectLatest { result ->
                val expected = UpdateEmployee(
                    data = UpdateEmployee.Data(
                        age = 26,
                        name = "Timo",
                        salary = "50000000"
                    ),
                    message = "Successfully! Record has been updated.",
                    status = "success"
                )
                when (result) {
                    is Resource.Success -> {
                        val actual: UpdateEmployee = result.data!!
                        assertEquals(expected.data, actual.data)
                    }
                    else -> {
                        val actual = result.data
                        assertNotEquals(expected, actual)
                    }
                }
            }
        }
    }

    @Test
    fun `shouldn't update employee correctly given 429 response`() {
        mockWebServer.enqueueResponse("429-response.json", 429)

        runBlocking {
            updateEmployeeRepository.updateEmployee(
                employeeId = "1",
                body = UpdateEmployee.Data(
                    name = "Timo",
                    salary = "50000000",
                    age = 26
                )
            ).collectLatest { result ->
                val expected = "To many request: 429, please try again later"
                when (result) {
                    is Resource.Error -> {
                        val actual: String? = result.message
                        assertEquals(expected, actual)
                        println("actual-assertEquals: $actual")
                        println("expected-assertEquals: $expected")
                    }
                    else -> {
                        val actual: String? = result.message
                        assertNotEquals(expected, actual)
                        println("actual-assertNotEquals: $actual")
                        println("expected-assertNotEquals: $expected")
                    }
                }
            }
        }
    }

    @Test
    fun `should delete employee correctly given 200 response`() {
        mockWebServer.enqueueResponse("delete-response.json", 200)

        runBlocking {
            val expected = DeleteEmployee(
                `data` = "1",
                message = "Successfully! Record has been deleted",
                status = "success"
            )
            deleteEmployeeRepository.deleteEmployee(employeeId = "1").collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        val actual = result.data
                        assertEquals(expected.data, actual?.data.toString())
                        assertEquals(expected.message, actual?.message.toString())
                        assertEquals(expected.status, actual?.status.toString())
                    }
                    else -> {
                        val actual = result.data
                        assertNotEquals(expected.data, actual?.data.toString())
                        assertNotEquals(expected.message, actual?.message.toString())
                        assertNotEquals(expected.status, actual?.status.toString())
                    }
                }
            }
        }
    }

    @Test
    fun `shouldn't delete employee correctly given 429 response`() {
        mockWebServer.enqueueResponse("429-response.json", 429)

        runBlocking {
            deleteEmployeeRepository.deleteEmployee(
                employeeId = "1"
            ).collectLatest { result ->
                val expected = "To many request: 429, please try again later"
                when (result) {
                    is Resource.Error -> {
                        val actual: String? = result.message
                        assertEquals(expected, actual)
                        println("actual-assertEquals: $actual")
                        println("expected-assertEquals: $expected")
                    }
                    else -> {
                        val actual: String? = result.message
                        assertNotEquals(expected, actual)
                        println("actual-assertNotEquals: $actual")
                        println("expected-assertNotEquals: $expected")
                    }
                }
            }
        }
    }
}
