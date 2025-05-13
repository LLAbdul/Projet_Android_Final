package com.example.evaluation4_android.ui.listetaches

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.evaluation4_android.model.Tache
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ItemTache(
    tache: Tache,
    onTacheCliquee: () -> Unit,
    onTacheCompleteeModifiee: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatteurDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateAchevementFormattee = try {
        tache.expectedDueDate?.let { Date(it) }?.let { formatteurDate.format(it) }
    } catch (e: Exception) {
        "Date invalide"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onTacheCliquee),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tache.nom,
                    style = MaterialTheme.typography.titleMedium,

                    textDecoration = if (tache.isCompleted) TextDecoration.LineThrough else null
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Échéance: $dateAchevementFormattee",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )

                tache.note?.let { note ->
                    if (note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Note: $note",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(

                    text = "Priorité: ${tache.priorite.name}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Checkbox(
                checked = tache.isCompleted,
                onCheckedChange = onTacheCompleteeModifiee,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
