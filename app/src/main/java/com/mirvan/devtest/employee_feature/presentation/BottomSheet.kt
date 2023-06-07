package com.mirvan.devtest.employee_feature.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // ktlint-disable no-wildcard-imports
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    bottomSheetShowState: MutableState<Boolean>,
    sheetState: SheetState,
    content: @Composable () -> Unit
) {
    var onDismiss by remember {
        mutableStateOf(false)
    }
    if (onDismiss) {
        LaunchedEffect(Unit) {
            this.launch {
                bottomSheetShowState.value = false
                sheetState.hide()
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss = true },
        sheetState = sheetState,
        shape = RoundedCornerShape(16.dp)
    ) {
        content()
    }
    LaunchedEffect(Unit) {
        sheetState.expand()
    }
}
