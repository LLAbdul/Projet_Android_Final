import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.LowPriority
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import com.example.evaluation4_android.model.Priorite
import com.example.evaluation4_android.model.toLocalizedString

@Composable
fun PrioriteChip(priorite: Priorite) {
    val context = LocalContext.current
    val couleurFond = getCouleurFond(priorite)
    val couleurTexte = getCouleurTexte(priorite)

    val chipIcon = when (priorite) {
        Priorite.ELEVE -> Icons.Default.PriorityHigh
        Priorite.MOYEN -> Icons.Default.Alarm
        Priorite.BAS -> Icons.Default.LowPriority
    }

    var animateIcon by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (animateIcon) 1.3f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = {
            animateIcon = false
        },
        label = "icon scale"
    )

    AssistChip(
        onClick = { animateIcon = true },
        label = { Text(text = priorite.toLocalizedString(context)) },
        leadingIcon = {
            Icon(
                imageVector = chipIcon,
                contentDescription = null,
                modifier = Modifier.scale(scale)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = couleurFond,
            labelColor = couleurTexte,
            leadingIconContentColor = couleurTexte
        )
    )
}
@Composable
fun getCouleurFond(priorite: Priorite) = when (priorite) {
    Priorite.ELEVE -> MaterialTheme.colorScheme.errorContainer
    Priorite.MOYEN -> MaterialTheme.colorScheme.tertiaryContainer
    Priorite.BAS -> MaterialTheme.colorScheme.secondaryContainer
}

@Composable
fun getCouleurTexte(priorite: Priorite) = when (priorite) {
    Priorite.ELEVE -> MaterialTheme.colorScheme.onErrorContainer
    Priorite.MOYEN -> MaterialTheme.colorScheme.onTertiaryContainer
    Priorite.BAS -> MaterialTheme.colorScheme.onSecondaryContainer
}

