package com.example.evaluation4_android.ui.ecrans

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluation4_android.R
import com.example.evaluation4_android.TachesApplication
import com.example.evaluation4_android.model.Priorite
import com.example.evaluation4_android.ui.composantes.ChampTacheForm
import com.example.evaluation4_android.ui.composantes.SelectionDateEcheance
import com.example.evaluation4_android.ui.state.AjoutTacheDTO
import com.example.evaluation4_android.ui.viewmodel.TacheViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranAjoutTache(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val tacheViewModel: TacheViewModel = viewModel(
        factory = (context.applicationContext as TachesApplication).tacheViewModelFactory
    )

    var nomTache by rememberSaveable { mutableStateOf("") }
    var noteTache by rememberSaveable { mutableStateOf("") }
    var dateEcheanceSelectionnee by rememberSaveable { mutableStateOf<Long?>(null) }
    var prioriteSelectionnee by rememberSaveable { mutableStateOf(Priorite.MOYEN) }
    var afficherDatePicker by remember { mutableStateOf(false) }

    val messageErreur by tacheViewModel.errorMessage.collectAsState()
    val estEnChargement by tacheViewModel.isLoading.collectAsState()

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
                title = { Text(stringResource(R.string.titre_ecran_ajouter_tache)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_retour)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
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
                estCompletee = false,
                onCompletionChange = {},
                estEnChargement = estEnChargement,
                boutonLabel = stringResource(R.string.bouton_enregistrer_tache),
                onSave = {
                    val formData = AjoutTacheDTO(
                        nom = nomTache.trim(),
                        note = noteTache.trim().ifBlank { null },
                        expectedDueDate = dateEcheanceSelectionnee,
                        priorite = prioriteSelectionnee
                    )
                    tacheViewModel.ajouterNouvelleTache(formData)
                    if (tacheViewModel.errorMessage.value == null && nomTache.isNotBlank() && dateEcheanceSelectionnee != null) {
                        onNavigateUp()
                    }
                },
                afficherCompletion = false
            )
        }
    }

    if (afficherDatePicker) {
        SelectionDateEcheance(
            initialDate = dateEcheanceSelectionnee,
            onDateSelected = { dateEcheanceSelectionnee = it },
            onDismiss = { afficherDatePicker = false }
        )
    }
}