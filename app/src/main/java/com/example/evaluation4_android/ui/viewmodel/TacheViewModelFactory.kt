package com.example.evaluation4_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evaluation4_android.data.repository.TacheRepository

class TacheViewModelFactory(private val repository: TacheRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TacheViewModel::class.java)) {
            return TacheViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}