package com.example.evaluation4_android.ui.composantes

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.util.*
import com.example.evaluation4_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionDateEcheance(
    initialDate: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate ?: Calendar.getInstance().timeInMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedMillisUTC = datePickerState.selectedDateMillis
                    if (selectedMillisUTC != null) {
                        val calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                        calendarUTC.timeInMillis = selectedMillisUTC

                        val calendarLocal = Calendar.getInstance()
                        calendarLocal.set(
                            calendarUTC.get(Calendar.YEAR),
                            calendarUTC.get(Calendar.MONTH),
                            calendarUTC.get(Calendar.DAY_OF_MONTH),
                            0, 0, 0
                        )
                        calendarLocal.set(Calendar.MILLISECOND, 0)

                        onDateSelected(calendarLocal.timeInMillis)
                    }
                    onDismiss()
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dialogue_annuler))
            }

        }
    ) {
        DatePicker(state = datePickerState)
    }
}
