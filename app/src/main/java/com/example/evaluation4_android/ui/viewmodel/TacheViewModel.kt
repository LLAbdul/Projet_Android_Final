package com.example.evaluation4_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluation4_android.data.repository.TacheRepository
import com.example.evaluation4_android.model.Priorite
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

    private val _tacheASupprimer = MutableStateFlow<Tache?>(null)
    val tacheASupprimer: StateFlow<Tache?> = _tacheASupprimer.asStateFlow()

    private val _nomTache = MutableStateFlow("")
    val nomTache = _nomTache.asStateFlow()

    private val _noteTache = MutableStateFlow("")
    val noteTache = _noteTache.asStateFlow()

    private val _dateEcheance = MutableStateFlow<Long?>(null)
    val dateEcheance = _dateEcheance.asStateFlow()

    private val _priorite = MutableStateFlow(Priorite.MOYEN)
    val priorite = _priorite.asStateFlow()

    private val _estCompletee = MutableStateFlow(false)
    val estCompletee = _estCompletee.asStateFlow()

    private val _ajoutNomTache = MutableStateFlow("")
    val ajoutNomTache = _ajoutNomTache.asStateFlow()

    private val _ajoutNoteTache = MutableStateFlow("")
    val ajoutNoteTache = _ajoutNoteTache.asStateFlow()

    private val _ajoutDateEcheance = MutableStateFlow<Long?>(null)
    val ajoutDateEcheance = _ajoutDateEcheance.asStateFlow()

    private val _ajoutPriorite = MutableStateFlow(Priorite.MOYEN)
    val ajoutPriorite = _ajoutPriorite.asStateFlow()


    fun modifierNomTache(nom: String) {
        _nomTache.value = nom
    }
    fun modifierNoteTache(note: String) {
        _noteTache.value = note
    }
    fun modifierDateEcheance(date: Long?) {
        _dateEcheance.value = date
    }
    fun modifierPriorite(priorite: Priorite) {
        _priorite.value = priorite
    }
    fun modifierEtatCompletion(etat: Boolean) {
        _estCompletee.value = etat
    }

    fun selectionnerTachePourSuppression(tache: Tache) {
        _tacheASupprimer.value = tache
    }

    fun annulerSuppression() {
        _tacheASupprimer.value = null
    }

    fun modifierAjoutNom(nom: String) {
        _ajoutNomTache.value = nom
    }
    fun modifierAjoutNote(note: String) {
        _ajoutNoteTache.value = note
    }
    fun modifierAjoutDate(date: Long?) {
        _ajoutDateEcheance.value = date
    }
    fun modifierAjoutPriorite(p: Priorite) {
        _ajoutPriorite.value = p
    }

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

    fun modifierTache(tacheAModifier: Tache) {
        if (tacheAModifier.nom.isBlank()) {
            _errorMessage.value = "Le nom ne peut pas être vide lors de la modification."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateTache(tacheAModifier)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la modification de la tâche: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun supprimerTache(tache: Tache) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteTache(tache)
                _errorMessage.value = null
                if (_selectedTacheState.value?.id == tache.id) {
                    _selectedTacheState.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la suppression de la tâche: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changerEtatCompletionTache(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateTacheCompletedStatus(id, isCompleted)
                _errorMessage.value = null
                if (_selectedTacheState.value?.id == id) {
                    _selectedTacheState.value =
                        _selectedTacheState.value?.copy(isCompleted = isCompleted)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors du changement d'état: ${e.message}"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
