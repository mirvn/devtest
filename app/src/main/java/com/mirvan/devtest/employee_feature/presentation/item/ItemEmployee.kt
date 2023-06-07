package com.mirvan.devtest.employee_feature.presentation.item // ktlint-disable package-name

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mirvan.devtest.R
import com.mirvan.devtest.employee_feature.domain.model.Employees
import com.mirvan.devtest.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEmployee(
    modifier: Modifier = Modifier,
    onCLickDetail: () -> Unit = {},
    employee: Employees.DataEmployee
) {
    ElevatedCard(
        modifier = modifier
            .wrapContentSize()
            .padding(bottom = 8.dp),
        onClick = onCLickDetail,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = Black.copy(0.8f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.id_label),
                    modifier = modifier.wrapContentSize().padding(start = 4.dp)
                )
                Text(
                    text = employee.id.toString(),
                    modifier = modifier.wrapContentSize()
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.name_label),
                    modifier = modifier.wrapContentSize().padding(start = 4.dp)
                )
                Text(
                    text = employee.employee_name.toString(),
                    modifier = modifier.wrapContentSize()
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.salary_label),
                    modifier = modifier.wrapContentSize().padding(start = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.rp) + employee.employee_salary.toString(),
                    modifier = modifier.wrapContentSize()
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.age_label),
                    modifier = modifier.wrapContentSize().padding(start = 4.dp)
                )
                Text(
                    text = employee.employee_age.toString() + stringResource(id = R.string.years),
                    modifier = modifier.wrapContentSize()
                )
            }
        }
    }
}
