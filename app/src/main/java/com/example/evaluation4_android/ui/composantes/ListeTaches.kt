package com.example.evaluation4_android.ui.composantes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evaluation4_android.model.Tache

@Composable
fun ListeTaches(
    taches: List<Tache>,
    onTacheCliquee: (Int) -> Unit,
    onEtatChange: (Int, Boolean) -> Unit,
    onTacheLongCliquee: (Tache) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(taches, key = { it.id }) { tache ->
            TacheCard(
                tache = tache,
                onTacheCliquee = { onTacheCliquee(tache.id) },
                onTacheCompleteeModifiee = { estCompletee ->
                    onEtatChange(tache.id, estCompletee)
                },
                onTacheLongCliquee = { onTacheLongCliquee(tache) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}