package com.example.evaluation4_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluation4_android.data.repository.TacheRepository
import com.example.evaluation4_android.model.Tache
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
}