package com.example.evaluation4_android.ui.composantes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.evaluation4_android.R
import com.example.evaluation4_android.model.Priorite
import com.example.evaluation4_android.model.toLocalizedString
import java.text.DateFormat
import java.util.*

@Composable
fun ChampTacheForm(
    nomTache: String,
    onNomChange: (String) -> Unit,
    noteTache: String,
    onNoteChange: (String) -> Unit,
    dateEcheance: Long?,
    onDateClick: () -> Unit,
    priorite: Priorite,
    onPrioriteChange: (Priorite) -> Unit,
    estCompletee: Boolean,
    onCompletionChange: (Boolean) -> Unit,
    estEnChargement: Boolean,
    onSave: () -> Unit,
    boutonLabel: String = stringResource(R.string.bouton_enregistrer),
    afficherCompletion: Boolean = true
) {
    val context = LocalContext.current
    val prioriteLabels = Priorite.entries.associateWith { it.toLocalizedString(context) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nomTache,
            onValueChange = onNomChange,
            label = { Text(stringResource(R.string.label_nom_tache_requis)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = noteTache,
            onValueChange = onNoteChange,
            label = { Text(stringResource(R.string.label_note_optionnelle)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dateEcheance?.let {
                    DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(Date(it))
                } ?: stringResource(R.string.label_date_echeance_requise),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Button(onClick = onDateClick) {
                Text(stringResource(R.string.bouton_choisir_date))
            }
        }

        Text(
            stringResource(R.string.label_priorite_requise),
            style = MaterialTheme.typography.titleSmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Priorite.entries.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPrioriteChange(it) }
                ) {
                    RadioButton(selected = it == priorite, onClick = { onPrioriteChange(it) })
                    Text(prioriteLabels[it] ?: it.name, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }

        if (afficherCompletion) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.label_completee),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = estCompletee, onCheckedChange = onCompletionChange)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = !estEnChargement && nomTache.isNotBlank() && dateEcheance != null
        ) {
            if (estEnChargement) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                Text(boutonLabel)
            }
        }
    }
}
