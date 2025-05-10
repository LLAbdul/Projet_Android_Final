package com.example.evaluation4_android.data.repository

import com.example.evaluation4_android.data.dao.TacheDao
import com.example.evaluation4_android.model.Tache
import kotlinx.coroutines.flow.Flow

class TacheRepository(private val tacheDao: TacheDao) {

    fun getAllTaches(): Flow<List<Tache>> {
        return tacheDao.getAllTaches()
    }

    fun getTacheById(id: Int): Flow<Tache?> {
        return tacheDao.getTacheById(id)
    }

    suspend fun ajouterTache(tache: Tache) {
        tacheDao.ajouterTache(tache)
    }

    suspend fun updateTache(tache: Tache) {
        tacheDao.updateTache(tache)
    }

    suspend fun deleteTache(tache: Tache) {
        tacheDao.deleteTache(tache)
    }

    suspend fun updateTacheCompletedStatus(id: Int, isCompleted: Boolean) {
        tacheDao.updateTacheCompletedStatus(id, isCompleted)
    }
}