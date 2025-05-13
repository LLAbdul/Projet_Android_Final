package com.example.evaluation4_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluation4_android.data.repository.TacheRepository
import com.example.evaluation4_android.model.Tache
import com.example.evaluation4_android.ui.state.AjoutTacheDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TacheViewModel(private val repository: TacheRepository) : ViewModel() {

    private val _taches = MutableStateFlow<List<Tache>>(emptyList())
    val taches: StateFlow<List<Tache>> = _taches.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedTacheState = MutableStateFlow<Tache?>(null)
    val selectedTacheState: StateFlow<Tache?> = _selectedTacheState.asStateFlow()

    init {
        chargerToutesLesTaches()
    }

    private fun chargerToutesLesTaches() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            repository.getAllTaches()
                .catch { exception ->
                    _errorMessage.value = exception.message ?: "Erreur"
                    _isLoading.value = false
                }
                .collect { taskList ->
                    _taches.value = taskList
                    _isLoading.value = false
                }
        }
    }

    fun ajouterNouvelleTache(tacheData: AjoutTacheDTO) {
        if (tacheData.nom.isBlank()) {
            _errorMessage.value = "Le nom de la tâche ne peut pas être vide."
            return
        }

        if (tacheData.expectedDueDate == null) {
            _errorMessage.value = "Veuillez sélectionner une date d'échéance prévue."
            return
        }

        val nouvelleTache = Tache(
            nom = tacheData.nom,
            note = tacheData.note,
            expectedDueDate = tacheData.expectedDueDate,
            priorite = tacheData.priorite
        )
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.ajouterTache(nouvelleTache)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de l'ajout de la tâche: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun chargerTacheParId(id: Int) {
        viewModelScope.launch {
            repository.getTacheById(id)
                .catch { exception ->
                    _errorMessage.value = "Erreur chargement tâche ID $id: ${exception.message}"
                    _selectedTacheState.value = null
                }
                .collect { tache ->
                    _selectedTacheState.value = tache
                }
        }
    }

}