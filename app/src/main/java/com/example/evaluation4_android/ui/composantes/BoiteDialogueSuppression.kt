package com.example.evaluation4_android.ui.composantes

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.evaluation4_android.model.Tache
import com.example.evaluation4_android.R

@Composable
fun BoiteDialogueSuppression(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    tache: Tache
) {
    val message = stringResource(R.string.message_dialogue_suppression_personnalisee, tache.nom)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.titre_dialogue_confirmation_suppression)) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.dialogue_supprimer))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dialogue_annuler))
            }
        }
    )
}
