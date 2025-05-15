package com.example.evaluation4_android.ui.composantes

import PrioriteChip
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluation4_android.R
import com.example.evaluation4_android.model.Priorite
import com.example.evaluation4_android.model.Tache
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TacheCard(
    tache: Tache,
    onTacheCliquee: () -> Unit,
    onTacheCompleteeModifiee: (Boolean) -> Unit,
    onTacheLongCliquee: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val formatteurDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val dateAchevementFormattee = try {
        tache.expectedDueDate?.let { Date(it) }?.let { formatteurDate.format(it) }
    } catch (e: Exception) {
        stringResource(R.string.date_invalide)
    }

    val interactionModifier = if (onTacheLongCliquee != null) {
        modifier.combinedClickable(
            onClick = onTacheCliquee,
            onLongClick = onTacheLongCliquee
        )
    } else {
        modifier.clickable(onClick = onTacheCliquee)
    }

    Card(
        modifier = interactionModifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = tache.nom,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (tache.isCompleted) TextDecoration.LineThrough else null,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(
                    R.string.label_echeance_prefix,
                    dateAchevementFormattee ?: ""
                ),
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic
            )

            tache.note?.takeIf { it.isNotBlank() }?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.label_note_prefix, it),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            tache.expectedDueDate?.let {
                CountdownText(it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrioriteChip(tache.priorite)
                AnimatedCheckbox(
                    checked = tache.isCompleted,
                    onCheckedChange = onTacheCompleteeModifiee
                )

            }
        }
    }
}

@Composable
fun AnimatedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (checked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "checkbox scale"
    )

    Box(modifier = Modifier.scale(scale)) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

