package com.example.evaluation4_android.ui.ecrans

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.evaluation4_android.ui.actions.supprimerTache
import com.example.evaluation4_android.ui.composantes.BoiteDialogueSuppression
import com.example.evaluation4_android.ui.composantes.ListeTaches
import com.example.evaluation4_android.ui.viewmodel.TacheViewModel
import androidx.compose.ui.res.stringResource
import com.example.evaluation4_android.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranListeTaches(
    viewModel: TacheViewModel,
    onNaviguerVersAjoutTache: () -> Unit,
    onNaviguerVersDetailTache: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val taches by viewModel.taches.collectAsState()
    val estEnChargement by viewModel.isLoading.collectAsState()
    val messageErreur by viewModel.errorMessage.collectAsState()

    val tacheASupprimer by viewModel.tacheASupprimer.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.titre_ecran_mes_taches)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNaviguerVersAjoutTache) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.description_bouton_ajouter_tache)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                estEnChargement -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                messageErreur != null -> {
                    Text(
                        text = messageErreur ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                taches.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.message_aucune_tache),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                else -> {
                    ListeTaches(
                        taches = taches,
                        onTacheCliquee = onNaviguerVersDetailTache,
                        onEtatChange = viewModel::changerEtatCompletionTache,
                        onTacheLongCliquee = { viewModel.selectionnerTachePourSuppression(it) }
                    )
                }
            }
        }

        tacheASupprimer?.let { tache ->
            BoiteDialogueSuppression(
                tache = tache,
                onDismiss = { viewModel.annulerSuppression() },
                onConfirm = {
                    supprimerTache(
                        context = context,
                        tacheViewModel = viewModel,
                        tache = tache,
                        onFinish = { viewModel.annulerSuppression() }
                    )
                }
            )
        }
    }
}