package com.mirvan.devtest.employee_feature.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirvan.devtest.Core.Utils.shimmerEffect
import com.mirvan.devtest.ui.theme.DevtestTheme

@Composable
fun ShimmerListItemEmployee(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Row(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary.copy(0.8f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.wrapContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun prevShimmerDistributor() {
    DevtestTheme {
        ShimmerListItemEmployee(
            isLoading = true,
            contentAfterLoading = {}
        )
    }
}
