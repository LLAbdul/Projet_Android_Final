package com.example.evaluation4_android.ui.ecrans

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluation4_android.TachesApplication
import com.example.evaluation4_android.model.Priorite
import com.example.evaluation4_android.ui.actions.supprimerTache
import com.example.evaluation4_android.ui.composantes.BoiteDialogueSuppression
import com.example.evaluation4_android.ui.composantes.ChampTacheForm
import com.example.evaluation4_android.ui.composantes.SelectionDateEcheance
import com.example.evaluation4_android.ui.viewmodel.TacheViewModel
import androidx.compose.ui.res.stringResource
import com.example.evaluation4_android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranDetailModificationTache(
    idTache: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tacheViewModel: TacheViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as TachesApplication).tacheViewModelFactory
    )

    LaunchedEffect(idTache) {
        tacheViewModel.chargerTacheParId(idTache)
    }

    val tacheSelectionnee by tacheViewModel.selectedTacheState.collectAsState()
    val messageErreur by tacheViewModel.errorMessage.collectAsState()
    val estEnChargement by tacheViewModel.isLoading.collectAsState()

    var nomTache by remember { mutableStateOf("") }
    var noteTache by remember { mutableStateOf("") }
    var dateEcheanceSelectionnee by remember { mutableStateOf<Long?>(null) }
    var prioriteSelectionnee by remember { mutableStateOf(Priorite.MOYEN) }
    var estCompletee by remember { mutableStateOf(false) }

    var afficherDatePicker by remember { mutableStateOf(false) }
    var afficherDialogSuppression by remember { mutableStateOf(false) }

    LaunchedEffect(tacheSelectionnee) {
        tacheSelectionnee?.let { tache ->
            nomTache = tache.nom
            noteTache = tache.note ?: ""
            dateEcheanceSelectionnee = tache.expectedDueDate
            prioriteSelectionnee = tache.priorite
            estCompletee = tache.isCompleted
        }
    }

    val context = LocalContext.current
    LaunchedEffect(messageErreur) {
        messageErreur?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            tacheViewModel.clearErrorMessage()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (tacheSelectionnee == null)
                            stringResource(R.string.texte_chargement)
                        else
                            stringResource(R.string.titre_ecran_modifier_tache)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_retour)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { afficherDialogSuppression = true }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.action_supprimer_tache)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (tacheSelectionnee == null && !estEnChargement) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.erreur_tache_introuvable))
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ChampTacheForm(
                    nomTache = nomTache,
                    onNomChange = { nomTache = it },
                    noteTache = noteTache,
                    onNoteChange = { noteTache = it },
                    dateEcheance = dateEcheanceSelectionnee,
                    onDateClick = { afficherDatePicker = true },
                    priorite = prioriteSelectionnee,
                    onPrioriteChange = { prioriteSelectionnee = it },
                    estCompletee = estCompletee,
                    onCompletionChange = { estCompletee = it },
                    estEnChargement = estEnChargement,
                    onSave = {
                        tacheSelectionnee?.let { tacheActuelle ->
                            val tacheModifiee = tacheActuelle.copy(
                                nom = nomTache.trim(),
                                note = noteTache.trim().ifBlank { null },
                                expectedDueDate = dateEcheanceSelectionnee
                                    ?: tacheActuelle.expectedDueDate,
                                priorite = prioriteSelectionnee,
                                isCompleted = estCompletee
                            )
                            tacheViewModel.modifierTache(tacheModifiee)

                            if (tacheViewModel.errorMessage.value == null && nomTache.isNotBlank() && dateEcheanceSelectionnee != null) {
                                onNavigateUp()
                            }
                        }
                    }
                )
            }
        }
    }

    if (afficherDatePicker) {
        SelectionDateEcheance(
            initialDate = dateEcheanceSelectionnee,
            onDateSelected = { dateEcheanceSelectionnee = it },
            onDismiss = { afficherDatePicker = false }
        )
    }

    tacheSelectionnee?.let { tache ->
        if (afficherDialogSuppression) {
            BoiteDialogueSuppression(
                tache = tache,
                onDismiss = { afficherDialogSuppression = false },
                onConfirm = {
                    supprimerTache(context, tacheViewModel, tache) {
                        afficherDialogSuppression = false
                        onNavigateUp()
                    }
                }
            )
        }
    }

}